package commands;

import managers.CollectionManager;
import network.requests.FilterGreaterRequest;
import network.requests.Request;
import network.responses.FilterByGenreResponse;
import network.responses.FilterGreaterResponse;
import network.responses.Response;
import structure.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class FilterGreaterThanOscarsCount extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterGreaterThanOscarsCount(CollectionManager collectionManager) {
        super("filter_greater_than_oscars_count oscarsCount", "вывести фильмы, значение поля oscars которых больше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        var req = (FilterGreaterRequest) request;
        try {
            return new FilterGreaterResponse(filterByOscarsCount(req.oscars), null);
        } catch (Exception e) {
            return new FilterGreaterResponse(null, e.toString());
        }
    }

    private List<Movie> filterByOscarsCount(Long oscars) {
        return collectionManager.getCollection().stream()
                .filter(movie -> (movie.getOscarsCount() > oscars))
                .collect(Collectors.toList());
    }
}
