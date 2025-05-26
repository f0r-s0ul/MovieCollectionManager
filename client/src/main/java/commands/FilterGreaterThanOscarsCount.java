package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.FilterGreaterRequest;
import network.responses.FilterGreaterResponse;
import structure.Movie;

import java.io.IOException;
import java.util.List;

public class FilterGreaterThanOscarsCount extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public FilterGreaterThanOscarsCount(Console console, UDPClient client) {
        super("filter_greater_than_oscars_count oscarsCount");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            long oscars = Long.parseLong(arguments[1]);
            var response = (FilterGreaterResponse) client.sendAndReceiveCommand(new FilterGreaterRequest(oscars));

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            List<Movie> movies = response.movies;
            if (movies.isEmpty()) {
                console.println("Фильмов с кол-вом оскаров больше чем " + oscars + " не обнаружено.");
            } else {
                console.println("Фильмов с кол-вом оскаров больше чем " + oscars + ": " + movies.size() + " шт.\n");
                movies.forEach(console::println);
            }

            return true;

        } catch (NumberFormatException exception) {
            console.printError("Кол-во оскаров должно быть представлено числом!");
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

