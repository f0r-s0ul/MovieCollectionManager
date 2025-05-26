package managers;

import commands.AbstractCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, AbstractCommand> commands = new HashMap<>();
    private final List<String> commandsHistory = new ArrayList<>();

    public void addCommand(String commandName, AbstractCommand command) {
        commands.put(commandName, command);
    }

    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    public void addCommandToHistory(String command) {
        commandsHistory.add(command);
    }

    public List<String> getCommandsHistory() {
        commandsHistory.removeIf(str -> str == null || str.trim().isEmpty());
        if (commandsHistory.size() <= 7) return commandsHistory;
        else return commandsHistory.subList(commandsHistory.size() - 7, commandsHistory.size());
    }

}
