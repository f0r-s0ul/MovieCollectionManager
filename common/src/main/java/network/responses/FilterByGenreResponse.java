package network.responses;

import structure.Movie;
import utilities.Commands;

import java.util.List;

public class FilterByGenreResponse extends Response{
    public final List<Movie> movies;

    public FilterByGenreResponse(List<Movie> movies, String error) {
        super(Commands.FILTER_BY_GENRE, error);
        this.movies = movies;
    }
}
