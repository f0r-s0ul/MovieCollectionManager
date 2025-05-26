package commands;

import managers.CollectionManager;
import network.requests.AddIfMaxRequest;
import network.requests.Request;
import network.responses.AddIfMaxResponse;
import network.responses.Response;
import structure.Movie;

public class AddIfMaxOscars extends AbstractCommand {
    private final CollectionManager collectionManager;

    public AddIfMaxOscars(CollectionManager collectionManager) {
        super("add_if_max_oscars {element}", "добавить новый фильм в коллекцию, если его кол-во оскаров превышает максимальное кол-во оскаров этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            var req = (AddIfMaxRequest) request;
            var maxOscars = maxOscars();
            if (req.movie.getOscarsCount() > maxOscars) {
                var newId = collectionManager.addMovie(req.movie);
                return new AddIfMaxResponse(true, newId, null);
            }
            return new AddIfMaxResponse(false, -1, null);
        } catch (Exception e) {
            return new AddIfMaxResponse(false, -1, e.toString());
        }
    }

    private Long maxOscars() {
        return collectionManager.getCollection().stream()
                .map(Movie::getOscarsCount)
                .mapToLong(Long::longValue)
                .max()
                .orElse(-1);
    }
}
