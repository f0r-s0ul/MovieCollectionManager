package utilities;

import java.io.Serializable;

public abstract class AbstractElement implements Comparable<AbstractElement>, Validable, Serializable {
    public abstract int getId();
}