package commands;

import builders.MovieBuilder;
import console.Console;
import exceptions.APIException;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.WrongAmountOfElementsException;
import network.UDPClient;
import network.requests.AddRequest;
import network.responses.AddResponse;

import java.io.IOException;

public class Add extends AbstractCommand{
    private final Console console;
    private final UDPClient client;

    public Add(Console console, UDPClient client) {
        super("add {element}");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean use(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание нового фильма:");

            var movie = (new MovieBuilder(console)).build();
            var response = (AddResponse) client.sendAndReceiveCommand(new AddRequest(movie));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }


            console.println("Фильм успешно добавлен!");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля фильма невалидны! Фильм не создан!");
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }catch (IncorrectInputInScriptException ignored) {}
        return false;
    }
}
