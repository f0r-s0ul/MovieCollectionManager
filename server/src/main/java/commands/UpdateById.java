package commands;

import managers.CollectionManager;
import network.requests.Request;
import network.requests.UpdateByIdRequest;
import network.responses.Response;
import network.responses.UpdateByIdResponse;

public class UpdateById extends AbstractCommand {
    private final CollectionManager collectionManager;

    public UpdateById(CollectionManager collectionManager) {
        super("update id {element}", "обновить значение фильма коллекции по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response use(Request request) {
        var req = (UpdateByIdRequest) request;
        try {
            if (collectionManager.getById(req.id) == null) {
                return new UpdateByIdResponse("Продукта с таким ID в коллекции нет!");
            }
            if (!req.movie.validate()) {
                return new UpdateByIdResponse( "Поля фильма не валидны! Продукт не обновлен!");
            }

            collectionManager.getById(req.id).update(req.movie);
            return new UpdateByIdResponse(null);
        } catch (Exception e) {
            return new UpdateByIdResponse(e.toString());
        }
    }
}