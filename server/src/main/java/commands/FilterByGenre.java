package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import network.requests.FilterByGenreRequest;
import network.requests.Request;
import network.responses.FilterByGenreResponse;
import network.responses.Response;
import structure.Movie;
import structure.MovieGenre;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByGenre extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterByGenre(CollectionManager collectionManager) {
        super("filter_by_genre genre", "вывести фильмы, значение поля genre которых равно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        var req = (FilterByGenreRequest) request;
        try {
            return new FilterByGenreResponse(filterByGenre(req.genre), null);
        } catch (Exception e) {
            return new FilterByGenreResponse(null, e.toString());
        }
    }

    private List<Movie> filterByGenre(MovieGenre genre) {
        return collectionManager.getCollection().stream()
                .filter(movie -> (movie.getGenre().equals(genre)))
                .collect(Collectors.toList());
    }
}
