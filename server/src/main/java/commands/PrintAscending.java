package commands;

import managers.CollectionManager;
import network.requests.Request;
import network.responses.PrintAscendingResponse;
import network.responses.Response;
import structure.Movie;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintAscending(CollectionManager collectionManager) {
        super("print_ascending", "вывести фильмы в порядке возрастания их оскаров");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            return new PrintAscendingResponse(sortMoviesAscending(), null);
        } catch (Exception e) {
            return new PrintAscendingResponse(null, e.toString());
        }
    }

    private List<Movie> sortMoviesAscending() {
        return collectionManager.getCollection().stream()
                .sorted(Comparator.comparingInt(Movie::getId))
                .collect(Collectors.toList());
    }
}
