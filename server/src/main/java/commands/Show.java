package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import network.requests.Request;
import network.responses.Response;
import network.responses.ShowResponse;

public class Show extends AbstractCommand {
    private final CollectionManager collectionManager;
    public Show(CollectionManager collectionManager) {
        super("show", "вывести все фильмы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        try {
            return new ShowResponse(collectionManager.getCollection(), null);
        } catch (Exception e) {
            return new ShowResponse(null, e.toString());
        }
    }
}

