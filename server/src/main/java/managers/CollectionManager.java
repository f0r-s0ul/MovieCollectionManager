package managers;

import java.time.LocalDateTime;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import structure.Movie;
import structure.Person;

public class CollectionManager {
    private HashSet<Movie> movies = new LinkedHashSet<>();
    private LocalDateTime lastSaveTime;
    private LocalDateTime lastInitTime;
    private final SaveLoadManager saveLoadManager;

    private static final Logger logger = LogManager.getLogger(CollectionManager.class);

    public CollectionManager(SaveLoadManager saveLoadManager) {
        this.saveLoadManager = saveLoadManager;
        this.lastSaveTime = null;
        this.lastInitTime = null;

        loadCollection();
    }



    public Integer getLastId() {
        int maxId = 0;
        for (Movie movie : movies) {
            if (movie.getId() > maxId) maxId = movie.getId();
        }
        return maxId;
    }

    public Person getScreenwriterByPassID(String id) {
        for (Movie movie : movies) {
            if (movie.getScreenwriter() != null) {
                if (movie.getScreenwriter().getPassportID().equals(id)) return movie.getScreenwriter();
            }
        }
        return null;
    }

    public String getCollectionInfo() {
        return "Collection info:\n" +
                "type: " + movies.getClass() + "\n" +
                "init date time: " + lastInitTime + "\n" +
                "count of values: " + movies.size();
    }

    private void loadCollection() {
        movies = (HashSet<Movie>) saveLoadManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    public void saveCollection() {
        saveLoadManager.writeCollection(movies);
        lastSaveTime = LocalDateTime.now();
    }

    public int addMovie(Movie movie) {
        var maxId = movies.stream().filter(Objects::nonNull)
                .map(Movie::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        var newId = maxId + 1;

        movies.add(movie.copy(newId));
        return newId;
    }

    public void clearCollection() {
        movies.clear();
    }

    public String getStringViewOfCollection() {
        StringBuilder s = new StringBuilder();
        for (Movie movie : movies) {
            s.append(movie.toString()).append("\n");
        }
        return s.toString();
    }

    public boolean validateAll() {
        for(var movie : new ArrayList<>(getCollection())) {
            if (!movie.validate()) {
                logger.warn("Фильм с id=" + movie.getId() + " имеет невалидные поля.");
                return false;
            }
            if (movie.getScreenwriter() != null) {
                if(!movie.getScreenwriter().validate()) {
                    logger.warn("Режисер фильма с id=" + movie.getId() + " имеет невалидные поля.");
                    return false;
                }
            }
        };
        logger.info("! Загруженные фильмы валидны.");
        return true;
    }

    public String getStringViewOfCollection(HashSet<Movie> collection) {
        StringBuilder s = new StringBuilder();
        for (Movie movie : collection) {
            s.append(movie.toString()).append("\n");
        }
        return s.toString();
    }

    public int countOfElements() {
        return movies.size();
    }

    public Movie getById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) return movie;
        }
        return null;
    }

    public void removeFromCollection(Movie element) {
        movies.remove(element);
    }

    public HashSet<Movie> getCollection() {
        return movies;
    }
}
