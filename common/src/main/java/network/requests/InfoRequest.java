package network.requests;

import utilities.Commands;

public class InfoRequest extends Request {
    public InfoRequest() {
        super(Commands.INFO);
    }
}
