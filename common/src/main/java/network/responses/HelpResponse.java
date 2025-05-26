package network.responses;

import utilities.Commands;

public class HelpResponse extends Response {
    public final String helpMessage;

    public HelpResponse(String helpMessage, String error) {
        super(Commands.HELP, error);
        this.helpMessage = helpMessage;
    }
}
