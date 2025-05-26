package commands;

import managers.CollectionManager;
import network.requests.Request;
import network.responses.ClearResponse;
import network.responses.Response;

public class Clear extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            collectionManager.clearCollection();
            return new ClearResponse(null);
        } catch (Exception e) {
            return new ClearResponse(e.toString());
        }
    }
}
