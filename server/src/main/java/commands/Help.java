package commands;

import managers.CommandManager;
import network.requests.Request;
import network.responses.HelpResponse;
import network.responses.Response;

public class Help extends AbstractCommand {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public Response use(Request request) {
        var helpMessage = new StringBuilder();

        commandManager.getCommands().values().forEach(command -> {
            helpMessage.append(" %-35s%-1s%n".formatted(command.getName(), command.getDescription()));
        });

        return new HelpResponse(helpMessage.toString(), null);
    }
}