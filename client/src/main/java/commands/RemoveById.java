package commands;

import console.Console;
import exceptions.APIException;
import exceptions.CollectionIsEmptyException;
import exceptions.NotFoundException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.RemoveByIdRequest;
import network.responses.RemoveByIdResponse;

import java.io.IOException;

public class RemoveById extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public RemoveById(Console console, UDPClient client) {
        super("remove_by_id id");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            int id = Integer.parseInt(arguments[1]);
            var response = (RemoveByIdResponse) client.sendAndReceiveCommand(new RemoveByIdRequest(id));

            if (response.getError() != null && !response.getError().isEmpty()) {
                if (response.getError().contains("Коллекция пуста")) {
                    throw new CollectionIsEmptyException();
                } else if (response.getError().contains("Фильма с таким ID в коллекции нет!")) {
                    throw new NotFoundException();
                } else {
                    throw new APIException(response.getError());
                }
            }

            console.println("Фильм успешно удален.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неверное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Фильма с таким ID в коллекции нет!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}