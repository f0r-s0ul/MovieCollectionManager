package network.responses;

import utilities.Commands;

public class RemoveByIdResponse extends Response {
    public RemoveByIdResponse(String error) {
        super(Commands.REMOVE_BY_ID, error);
    }
}
