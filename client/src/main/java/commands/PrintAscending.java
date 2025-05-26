package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.PrintAscendingRequest;
import network.responses.PrintAscendingResponse;
import structure.Movie;

import java.io.IOException;
import java.util.List;

public class PrintAscending extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public PrintAscending(Console console, UDPClient client) {
        super("print_ascending");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (PrintAscendingResponse) client.sendAndReceiveCommand(new PrintAscendingRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            List<Movie> sortedMovies = response.movies;
            if (sortedMovies.isEmpty()) {
                console.println("Коллекция фильмов пуста.");
            } else {
                console.println("Фильмы в порядке возрастания id (" + sortedMovies.size() + " шт.):");
                sortedMovies.forEach(console::println);
            }

            return true;

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
