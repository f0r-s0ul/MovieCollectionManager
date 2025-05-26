package network.requests;

import structure.Movie;
import utilities.Commands;

public class AddRequest extends Request {
    public final Movie movie;

    public AddRequest(Movie movie) {
        super(Commands.ADD);
        this.movie = movie;
    }
}
