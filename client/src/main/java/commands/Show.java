package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.ShowRequest;
import network.responses.ShowResponse;

import java.io.IOException;

public class Show extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public Show(Console console, UDPClient client) {
        super("show");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (ShowResponse) client.sendAndReceiveCommand(new ShowRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (!response.movies.isEmpty()) {
                response.movies.forEach(console::println);
            } else {
                console.println("Коллекция пуста");
            }

            return true;

        } catch (WrongAmountOfElementsException e) {
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