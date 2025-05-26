package structure;

import java.io.Serializable;

public enum MovieGenre implements Serializable {
    ACTION,
    COMEDY,
    ADVENTURE,
    SCIENCE_FICTION;

    private static final MovieGenre[] list = MovieGenre.values();

    public static String names() {
        StringBuilder namesList = new StringBuilder();
        for (var genre : values()) {
            namesList.append(genre.name()).append(", ");
        }
        return namesList.substring(0, namesList.length()-2);
    }

    public static MovieGenre byID(int i) {
        return list[i - 1];
    }

    public static int lastIndex() {
        return list.length;
    }
}
