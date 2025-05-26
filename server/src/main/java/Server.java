import commands.*;
import handlers.CommandHandler;
import managers.CollectionManager;
import managers.CommandManager;
import managers.SaveLoadManager;
import network.UDPDatagramServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {
    public static int PORT;
    public static final Logger logger = LogManager.getLogger(Server.class);

    public static void main(String[] args) {
        SaveLoadManager saveLoadManager = new SaveLoadManager();
        CollectionManager collectionManager = new CollectionManager(saveLoadManager);

        try {
            PORT = Integer.parseInt(args[0]);
            if (PORT < 32000 || PORT > 64000) {
                logger.fatal("Порт должен принимать значение в диапозоне от 32000 до 64000! (аргумент запуска 1)");
                System.exit(1);
            }
        } catch (Exception e) {
            logger.fatal("Значение порта должно быть числом в диапозоне от 32000 до 64000! (аргумент запуска 1)");
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(collectionManager::saveCollection));

        CommandManager commandManager = new CommandManager();
        commandManager.addCommand("add", new Add(collectionManager));
        commandManager.addCommand("add_if_max_oscars", new AddIfMaxOscars(collectionManager));
        commandManager.addCommand("clear", new Clear(collectionManager));
        commandManager.addCommand("filter_by_genre", new FilterByGenre(collectionManager));
        commandManager.addCommand("filter_greater_than_oscars_count", new FilterGreaterThanOscarsCount(collectionManager));
        commandManager.addCommand("help", new Help(commandManager));
        commandManager.addCommand("history", new History(commandManager));
        commandManager.addCommand("info", new Info(collectionManager));
        commandManager.addCommand("print_ascending", new PrintAscending(collectionManager));
        commandManager.addCommand("remove_by_id", new RemoveById(collectionManager));
        commandManager.addCommand("remove_greater_oscars", new RemoveGreaterOscars(collectionManager));
        commandManager.addCommand("show", new Show(collectionManager));
        commandManager.addCommand("update_by_id", new UpdateById(collectionManager));
        commandManager.addCommand("execute_script", new ExecuteScript());

        try {
            var server = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandHandler(commandManager));
            server.run();
        } catch (SocketException e) {
            logger.fatal("Случилась ошибка сокета", e);
        } catch (UnknownHostException e) {
            logger.fatal("Неизвестный хост", e);
        }
    }
}