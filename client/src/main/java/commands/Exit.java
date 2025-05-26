package commands;

import console.Console;

public class Exit extends AbstractCommand {
    private final Console console;

    public Exit(Console console) {
        super("exit");
        this.console = console;
    }

    @Override
    public boolean use(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение выполнения");
        System.exit(0);
        return true;
    }
}