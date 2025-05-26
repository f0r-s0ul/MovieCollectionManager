package commands;

import managers.CollectionManager;
import network.requests.AddRequest;
import network.requests.Request;
import network.responses.AddResponse;
import network.responses.Response;

public class Add extends AbstractCommand{
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            var req = (AddRequest) request;
            var newId = collectionManager.addMovie(req.movie);
            return new AddResponse(newId, null);
        } catch (Exception e) {
            return new AddResponse(-1, e.toString());
        }
    }
}
