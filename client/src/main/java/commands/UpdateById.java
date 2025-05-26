package commands;

import builders.MovieBuilder;
import console.Console;
import exceptions.APIException;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.NotFoundException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.UpdateByIdRequest;
import network.responses.UpdateByIdResponse;

import java.io.IOException;

public class UpdateById extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public UpdateById(Console console, UDPClient client) {
        super("update id {element}");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            int id = Integer.parseInt(arguments[1]);
            console.println("* Введите данные обновленного фильма:");
            console.ps2();

            var newMovie = (new MovieBuilder(console)).build();
            var response = (UpdateByIdResponse) client.sendAndReceiveCommand(
                    new UpdateByIdRequest(id, newMovie));

            if (response.getError() != null && !response.getError().isEmpty()) {
                if (response.getError().contains("Коллекция пуста")) {
                    throw new CollectionIsEmptyException();
                } else if (response.getError().contains("Продукта с таким ID в коллекции нет!")) {
                    throw new NotFoundException();
                } else {
                    throw new APIException(response.getError());
                }
            }

            console.println("Фильм успешно обновлен.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Фильма с таким ID в коллекции нет!");
        } catch (IncorrectInputInScriptException ignored) {
        } catch (InvalidFormException exception) {
            console.printError("Поля фильма не валидны! Фильм не обновлен!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}