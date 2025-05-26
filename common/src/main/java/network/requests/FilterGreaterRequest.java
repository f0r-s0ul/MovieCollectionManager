package network.requests;

import utilities.Commands;

public class FilterGreaterRequest extends Request {
    public final Long oscars;

    public FilterGreaterRequest(Long oscars) {
        super(Commands.FILTER_GREATER_THAN_OSCARS_COUNT);
        this.oscars = oscars;
    }
}
