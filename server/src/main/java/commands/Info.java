package commands;

import managers.CollectionManager;
import network.requests.Request;
import network.responses.InfoResponse;
import network.responses.Response;

public class Info extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            return new InfoResponse(collectionManager.getCollectionInfo(), null);
        } catch (Exception e) {
            return new InfoResponse(null, e.toString());
        }
    }
}
