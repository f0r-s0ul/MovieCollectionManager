package network.requests;

import utilities.Commands;

public class ClearRequest extends Request {
    public ClearRequest() {
        super(Commands.CLEAR);
    }
}

