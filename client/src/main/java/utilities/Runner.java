package utilities;

import commands.*;
import console.Console;
import exceptions.ScriptRecursionException;
import network.UDPClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final UDPClient client;
    private final Map<String, AbstractCommand> commands;

    private final List<String> scriptStack = new ArrayList<>();

    public Runner(UDPClient client, Console console) {
        this.console = console;
        this.client = client;
        ReadModeManager.setUserScanner(new Scanner(System.in));
        this.commands = new HashMap<>() {{
            put(Commands.ADD, new Add(console, client));
            put(Commands.ADD_IF_MAX_OSCARS, new AddIfMaxOscars(console, client));
            put(Commands.CLEAR, new Clear(console, client));
            put(Commands.EXIT, new Exit(console));
            put(Commands.FILTER_BY_GENRE, new FilterByGenre(console, client));
            put(Commands.FILTER_GREATER_THAN_OSCARS_COUNT, new FilterGreaterThanOscarsCount(console, client));
            put(Commands.HELP, new Help(console, client));
            put(Commands.HISTORY, new History(console, client));
            put(Commands.INFO, new Info(console, client));
            put(Commands.PRINT_ASCENDING, new PrintAscending(console, client));
            put(Commands.REMOVE_BY_ID, new RemoveById(console, client));
            put(Commands.REMOVE_GREATER_OSCARS, new RemoveGreaterOscars(console, client));
            put(Commands.SHOW, new Show(console, client));
            put(Commands.UPDATE_BY_ID, new UpdateById(console, client));
        }};
        this.commands.put(Commands.EXECUTE_SCRIPT, new ExecuteScript(console, this));
    }

    public void interactiveMode() {
        var userScanner = ReadModeManager.getUserScanner();
        try {
            ExitCode commandStatus;
            String[] userCommand = {"", ""};

            do {
                console.ps1();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandStatus = launchCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);

        } catch (NoSuchElementException exception) {
            console.printError("Программа завершена! (обнаружена EOF-последоваельность)");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }


    public ExitCode scriptMode(String argument) {
        String[] userCommand = {"", ""};
        ExitCode commandStatus;
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = ReadModeManager.getUserScanner();
            ReadModeManager.setUserScanner(scriptScanner);
            ReadModeManager.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                console.println(console.getPS1() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());

            ReadModeManager.setUserScanner(tmpScanner);
            ReadModeManager.setUserMode();

            if (commandStatus == ExitCode.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                console.println("Проверьте скрипт на корректность введенных данных!");
            }

            return commandStatus;

        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
        return ExitCode.ERROR;
    }

    private ExitCode launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        var command = commands.get(userCommand[0]);

        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commands.get("exit").use(userCommand)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!commands.get("execute_script").use(userCommand)) return ExitCode.ERROR;
            }
            default -> { if (!command.use(userCommand)) return ExitCode.ERROR; }
        };

        return ExitCode.OK;
    }
}