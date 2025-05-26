package network.requests;

import structure.Movie;
import utilities.Commands;

public class AddIfMaxRequest extends Request {
    public final Movie movie;

    public AddIfMaxRequest(Movie movie) {
        super(Commands.ADD_IF_MAX_OSCARS);
        this.movie = movie;
    }
}