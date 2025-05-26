package network.responses;

import utilities.Commands;

public class UpdateByIdResponse extends Response {
    public UpdateByIdResponse(String error) {
        super(Commands.UPDATE_BY_ID, error);
    }
}
