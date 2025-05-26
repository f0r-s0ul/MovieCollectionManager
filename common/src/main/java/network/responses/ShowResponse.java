package network.responses;

import structure.Movie;
import utilities.Commands;

import java.util.HashSet;

public class ShowResponse extends Response {
    public final HashSet<Movie> movies;

    public ShowResponse(HashSet<Movie> movies, String error) {
        super(Commands.SHOW, error);
        this.movies = movies;
    }
}