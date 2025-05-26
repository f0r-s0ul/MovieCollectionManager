package network.requests;

import structure.Movie;
import utilities.Commands;

public class UpdateByIdRequest extends Request {
    public final int id;
    public final Movie movie;

    public UpdateByIdRequest(int id, Movie movie) {
        super(Commands.UPDATE_BY_ID);
        this.id = id;
        this.movie = movie;
    }
}
