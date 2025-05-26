package network.requests;

import structure.Movie;
import utilities.Commands;

public class RemoveGreaterRequest extends Request{
    public final Long oscars;

    public RemoveGreaterRequest(Long oscars) {
        super(Commands.REMOVE_GREATER_OSCARS);
        this.oscars = oscars;
    }
}
