package network.requests;

import utilities.Commands;

public class HistoryRequest extends Request{
    public HistoryRequest() {
        super(Commands.HISTORY);
    }
}
