package network.responses;

import utilities.Commands;

public class HistoryResponse extends Response {
    public final String history;

    public HistoryResponse(String history, String error) {
        super(Commands.HISTORY, error);
        this.history = history;
    }
}
