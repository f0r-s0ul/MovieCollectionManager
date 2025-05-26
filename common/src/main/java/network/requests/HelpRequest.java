package network.requests;

import utilities.Commands;

public class HelpRequest extends Request {
    public HelpRequest() {
        super(Commands.HELP);
    }
}
