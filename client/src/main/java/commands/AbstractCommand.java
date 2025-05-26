package commands;

import java.util.Objects;

public abstract class AbstractCommand {
    private String name;

    public AbstractCommand(String name) {
        this.name = name;
    }

    public boolean resolve(String name) {
        return name.equals(this.name);
    }

    public String getName() {
        return name;
    }

    public abstract boolean use(String[] args);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCommand other = (AbstractCommand) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Command name: " + name;
    }
}
