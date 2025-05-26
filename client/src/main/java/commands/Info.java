package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.InfoRequest;
import network.responses.InfoResponse;

import java.io.IOException;

public class Info extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public Info(Console console, UDPClient client) {
        super("info");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (InfoResponse) client.sendAndReceiveCommand(new InfoRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println(response.infoMessage);
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
