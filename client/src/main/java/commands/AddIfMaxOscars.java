package commands;

import builders.MovieBuilder;
import console.Console;
import exceptions.APIException;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.AddIfMaxRequest;
import network.responses.AddIfMaxResponse;

import java.io.IOException;

public class AddIfMaxOscars extends AbstractCommand {
    private final Console console;
    private final UDPClient client;

    public AddIfMaxOscars(Console console, UDPClient client) {
        super("add_if_max_oscars {element}");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание нового фильма (add_if_max_oscars):");
            var movie = (new MovieBuilder(console)).build();

            var response = (AddIfMaxResponse) client.sendAndReceiveCommand(new AddIfMaxRequest(movie));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (response.isAdded) {
                console.println("Фильм успешно добавлен!");
            } else {
                console.println("Фильм не добавлен, кол-во оскаров не максимальное");
            }
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля фильма не валидны! Фильм не создан!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (IncorrectInputInScriptException ignored) {}
        return false;
    }
}
