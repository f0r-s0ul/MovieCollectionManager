package commands;

import console.Console;
import exceptions.APIException;
import network.UDPClient;
import network.requests.HelpRequest;
import network.responses.HelpResponse;

import java.io.IOException;
import java.util.Map;

public class Help extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public Help(Console console, UDPClient client) {
        super("help");
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

            var response = (HelpResponse) client.sendAndReceiveCommand(new HelpRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Справка по командам:");
            String helpMessage = response.helpMessage;
            console.println(helpMessage);

            return true;

        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}