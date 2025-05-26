package commands;

import console.Console;
import exceptions.APIException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.RemoveGreaterRequest;
import network.responses.RemoveGreaterResponse;

import java.io.IOException;

public class RemoveGreaterOscars extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public RemoveGreaterOscars(Console console, UDPClient client) {
        super("remove_greater_oscars oscars");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            long oscars = Long.parseLong(arguments[1]);
            var response = (RemoveGreaterResponse) client.sendAndReceiveCommand(new RemoveGreaterRequest(oscars));

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            int count = response.count;
            if (count == 0) {
                console.println("Фильмов с кол-вом оскаров больше чем " + oscars + " не обнаружено.");
            } else {
                console.println("Фильмов с кол-вом оскаров больше чем " + oscars + " удалено: " + count + " шт.");
            }

            return true;

        } catch (NumberFormatException exception) {
            console.printError("Кол-во оскаров должно быть представлено числом!");
            console.println("Использование: '" + getName() + "'");
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
