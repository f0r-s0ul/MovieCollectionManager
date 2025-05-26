package handlers;

import managers.CommandManager;
import network.requests.Request;
import network.responses.NoSuchCommandResponse;
import network.responses.Response;

public class CommandHandler {
    private final CommandManager manager;

    public CommandHandler(CommandManager manager) {
        this.manager = manager;
    }

    public Response handle(Request request) {
        var command = manager.getCommands().get(request.getName());
        if (command == null) return new NoSuchCommandResponse(request.getName());
        manager.addCommandToHistory(command.getName());
        return command.use(request);
    }
}