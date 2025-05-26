package commands;

import console.Console;
import exceptions.ScriptRecursionException;
import exceptions.WrongAmountOfElementsException;
import utilities.Runner;

public class ExecuteScript extends AbstractCommand {
    private final Console console;
    private final Runner runner;

    public ExecuteScript(Console console, Runner runner) {
        super("execute_script <file_path>");
        this.console = console;
        this.runner = runner;
    }

    @Override
    public boolean use(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();


            console.println("Выполнение скрипта '" + args[1] + "':");
            runner.scriptMode(args[1]);

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (ScriptRecursionException exception) {
            console.printError("Обнаружена рекурсия! Скрипт не выполнен.");
        }
        return false;
    }
}