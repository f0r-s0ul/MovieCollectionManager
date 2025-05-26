package commands;

import managers.CommandManager;
import network.requests.Request;
import network.responses.Response;

public class ExecuteScript extends AbstractCommand{

    public ExecuteScript() {
        super("execute_script <file_path>", "выполнить команды из файла");
    }

    @Override
    public Response use(Request request) {
        return null;
    }
}
