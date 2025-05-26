package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.HistoryRequest;
import network.responses.HistoryResponse;

import java.io.IOException;
import java.util.List;

public class History extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public History(Console console, UDPClient client) {
        super("history");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (HistoryResponse) client.sendAndReceiveCommand(new HistoryRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            String history = response.history;
            console.println(history);

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