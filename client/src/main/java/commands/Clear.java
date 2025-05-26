package commands;

import console.Console;
import exceptions.APIException;
import network.UDPClient;
import network.requests.ClearRequest;
import network.responses.ClearResponse;

import java.io.IOException;

public class Clear extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public Clear(Console console, UDPClient client) {
        super("clear");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.println("Использование: '" + getName() + "'");
                return false;
            }

            var response = (ClearResponse) client.sendAndReceiveCommand(new ClearRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Коллекция очищена!");
            return true;

        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
