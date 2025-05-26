package network.responses;

import structure.Movie;
import utilities.Commands;

import java.util.List;

public class FilterGreaterResponse extends Response {
    public final List<Movie> movies;

    public FilterGreaterResponse(List<Movie> movies, String error) {
        super(Commands.FILTER_GREATER_THAN_OSCARS_COUNT, error);
        this.movies = movies;
    }
}
