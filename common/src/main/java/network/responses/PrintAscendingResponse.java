package network.responses;

import structure.Movie;
import utilities.Commands;

import java.util.List;

public class PrintAscendingResponse extends Response {
    public final List<Movie> movies;

    public PrintAscendingResponse(List<Movie> movies, String error) {
        super(Commands.PRINT_ASCENDING, error);
        this.movies = movies;
    }
}
