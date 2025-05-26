package builders;

import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;

public abstract class AbstractBuilder<T> {
    public abstract T build() throws IncorrectInputInScriptException, InvalidFormException;
}
