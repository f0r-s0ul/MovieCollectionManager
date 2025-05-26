package structure;

import java.io.Serializable;

public enum MpaaRating implements Serializable {
    G,
    PG,
    PG_13,
    R,
    NC_17;

    private static final MpaaRating[] list = MpaaRating.values();

    public static String names() {
        StringBuilder namesList = new StringBuilder();
        for (var rating : values()) {
            namesList.append(rating.name()).append(", ");
        }
        return namesList.substring(0, namesList.length()-2);
    }

    public static MpaaRating byID(int i) {
        return list[i - 1];
    }

    public static int lastIndex() {
        return list.length;
    }
}
