package network.requests;

import structure.MovieGenre;
import utilities.Commands;

public class FilterByGenreRequest extends Request {
    public final MovieGenre genre;

    public FilterByGenreRequest(MovieGenre genre) {
        super(Commands.FILTER_BY_GENRE);
        this.genre = genre;
    }
}
