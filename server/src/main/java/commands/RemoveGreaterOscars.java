package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import network.requests.RemoveGreaterRequest;
import network.requests.Request;
import network.responses.RemoveGreaterResponse;
import network.responses.Response;
import structure.Movie;

public class RemoveGreaterOscars extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveGreaterOscars(CollectionManager collectionManager) {
        super("remove_greater_oscars oscars", "удалить фильмы кол-во оскаров которых превышает заданное");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            var req = (RemoveGreaterRequest) request;

            var count = 0;
            count = remove(req.oscars);

            return new RemoveGreaterResponse(count, null);

        } catch (Exception e) {
            return new RemoveGreaterResponse(0, e.toString());
        }
    }

    private int remove(Long oscars) {
        var count = 0;
        for(Movie movie : collectionManager.getCollection()) {
            if (movie.getOscarsCount() > oscars) {
                collectionManager.removeFromCollection(movie);
                count += 1;
            }
        }
        return count;
    }
}
