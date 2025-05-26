package structure;

import java.io.Serializable;

public enum Color implements Serializable {
    GREEN,
    RED,
    BLUE,
    WHITE;

    private static final Color[] list = Color.values();

    public static String names() {
        StringBuilder namesList = new StringBuilder();
        for (var color : values()) {
            namesList.append(color.name()).append(", ");
        }
        return namesList.substring(0, namesList.length()-2);
    }

    public static Color byID(int i) {
        return list[i - 1];
    }

    public static int lastIndex() {
        return list.length;
    }
}

