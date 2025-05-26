package commands;

import network.requests.Request;
import network.responses.Response;

import java.util.Objects;

public abstract class AbstractCommand {
    private String name;
    private String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract Response use(Request request);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCommand other = (AbstractCommand) o;
        return Objects.equals(name, other.name) && Objects.equals(description, other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Command name: " + name + " || " + "description: " + description;
    }
}
