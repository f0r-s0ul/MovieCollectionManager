package structure;

import utilities.AbstractElement;

import java.time.LocalDateTime;
import java.util.Objects;

public class Movie extends AbstractElement {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long oscarsCount; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person screenwriter;

    public Movie(Integer id, String name, Coordinates coordinates, long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (oscarsCount < 0) return false;
        return mpaaRating != null;
    }

    public void update(Movie movie) {
        this.name = movie.name;
        this.coordinates = movie.coordinates;
        this.creationDate = movie.creationDate;
        this.oscarsCount = movie.oscarsCount;
        this.genre = movie.genre;
        this.mpaaRating = movie.mpaaRating;
        this.screenwriter = movie.screenwriter;
    }

    @Override
    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }
    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }
    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime date) {
        this.creationDate = date;
    }

    public Movie copy(int id) {
        return new Movie(id, this.name, this.coordinates, this.oscarsCount, this.genre, this.mpaaRating, this.screenwriter);
    }

    @Override
    public int compareTo(AbstractElement element) {
        return Integer.compare(id, element.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie other = (Movie) o;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(coordinates, other.coordinates)
                && Objects.equals(creationDate, other.creationDate) && Objects.equals(oscarsCount, other.oscarsCount)
                && Objects.equals(mpaaRating, other.mpaaRating) && Objects.equals(genre, other.genre)
                && Objects.equals(screenwriter, other.screenwriter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, oscarsCount, mpaaRating, genre, screenwriter);
    }

    @Override
    public String toString() {
        String msg;

        String genreToMsg;
        String screenwriterToMsg;

        if (genre == null) genreToMsg = "Unknown"; else genreToMsg = String.valueOf(genre);
        if (screenwriter == null) screenwriterToMsg = "Unknown"; else screenwriterToMsg = screenwriter.toString();

        msg = "Movie: \n" +
              "id: " + id + ", \n" +
              "name: " + name + ", \n" +
              "coordinates: " + coordinates.toString() + ", \n" +
              "creation date and time: " + creationDate + ", \n" +
              "oscars count: " + oscarsCount + ", \n" +
              "genre: " + genreToMsg + ", \n" +
              "MPAA rating: " + mpaaRating + ", \n" +
              "screenwriter: " + screenwriterToMsg + "\n";

        return msg;
    }
}

