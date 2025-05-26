package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.FilterByGenreRequest;
import network.responses.FilterByGenreResponse;
import structure.Movie;
import structure.MovieGenre;

import java.io.IOException;
import java.util.List;

public class FilterByGenre extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public FilterByGenre(Console console, UDPClient client) {
        super("filter_by_genre genre");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var genre = MovieGenre.valueOf(arguments[1].toUpperCase());
            var response = (FilterByGenreResponse) client.sendAndReceiveCommand(new FilterByGenreRequest(genre));

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            List<Movie> movies = response.movies;
            if (movies.isEmpty()) {
                console.println("Фильмов с жанром " + genre + " не обнаружено.");
            } else {
                console.println("Фильмов с жанром " + genre + ": " + movies.size() + " шт.\n");
                movies.forEach(console::println);
            }

            return true;

        } catch (IllegalArgumentException exception) {
            console.printError("Такого жанра не существует!");
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
