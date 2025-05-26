package commands;

import managers.CollectionManager;
import network.requests.RemoveByIdRequest;
import network.requests.Request;
import network.responses.RemoveByIdResponse;
import network.responses.Response;

public class RemoveById extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id id", "удалить фильм из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        var req = (RemoveByIdRequest) request;

        try {
            if (collectionManager.getById(req.id) == null) {
                return new RemoveByIdResponse("Фильма с таким ID в коллекции нет!");
            }

            collectionManager.removeFromCollection(collectionManager.getById(req.id));
            return new RemoveByIdResponse(null);
        } catch (Exception e) {
            return new RemoveByIdResponse(e.toString());
        }
    }
}
