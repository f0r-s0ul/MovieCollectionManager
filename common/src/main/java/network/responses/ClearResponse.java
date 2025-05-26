package network.responses;

import utilities.Commands;

public class ClearResponse extends Response {
    public ClearResponse(String error) {
        super(Commands.CLEAR, error);
    }
}
