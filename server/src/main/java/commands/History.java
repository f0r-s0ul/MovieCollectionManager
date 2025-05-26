package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CommandManager;
import network.requests.FilterGreaterRequest;
import network.requests.HistoryRequest;
import network.requests.Request;
import network.responses.FilterByGenreResponse;
import network.responses.HistoryResponse;
import network.responses.Response;

import java.util.List;

public class History extends AbstractCommand {
    private final CommandManager commandManager;

    public History(CommandManager commandManager) {
        super("history", "вывести последние 7 команд");
        this.commandManager = commandManager;
    }

    @Override
    public Response use(Request request) {
        var req = (HistoryRequest) request;
        try {
            String historyMessage = "История команд:";
            List<String> history = commandManager.getCommandsHistory();
            for (int i = 0; i < history.size(); i += 1) {
                historyMessage = historyMessage + "\n" + history.get(i);
            }

            return new HistoryResponse(historyMessage, null);
        } catch (Exception e) {
            return new FilterByGenreResponse(null, e.toString());
        }
    }
}
