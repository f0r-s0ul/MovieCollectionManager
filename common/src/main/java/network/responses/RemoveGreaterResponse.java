package network.responses;

import utilities.Commands;

public class RemoveGreaterResponse extends Response {
    public final int count;

    public RemoveGreaterResponse(int count, String error) {
        super(Commands.REMOVE_GREATER_OSCARS, error);
        this.count = count;
    }
}
