package managers;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import structure.*;

public class SaveLoadManager {
    private final String fileName;
    private final static Logger logger = LogManager.getLogger(SaveLoadManager.class);
    private static final String ENV_VAR_NAME = "COLLECTION_FILE";

    public SaveLoadManager() {
        this.fileName = getFileNameFromENV();
    }

    private String getFileNameFromENV() {
        String envFile = System.getenv(ENV_VAR_NAME);
        if (envFile == null || envFile.isEmpty()) {
            logger.error("Переменная окружения " + ENV_VAR_NAME + " не установлена!");
            return null;
        }
        return envFile;
    }

    public void writeCollection(HashSet<Movie> collection) {
        if (fileName == null || fileName.isEmpty()) {
            logger.error("Имя файла не указано!");
            return;
        }

        logger.info("Начало сохранения коллекции в файл: " + fileName);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("movies");
            writer.writeCharacters("\n");

            int count = 0;
            for (Movie movie : collection) {
                writeMovie(writer, movie);
                writer.writeCharacters("\n");
                count++;
            }

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();

            logger.info("Успешно сохранено " + count + " фильмов в файл: " + fileName);
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода при записи в файл: " + e.getMessage());
        } catch (XMLStreamException e) {
            logger.error("Ошибка формирования XML: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при сохранении: " + e.getMessage());
        }
    }

    private void writeMovie(XMLStreamWriter writer, Movie movie) throws XMLStreamException {
        try {
            writer.writeStartElement("movie");
            writeElement(writer, "id", String.valueOf(movie.getId()));
            writeElement(writer, "name", movie.getName());

            writer.writeStartElement("coordinates");
            writeElement(writer, "x", String.valueOf(movie.getCoordinates().getX()));
            writeElement(writer, "y", String.valueOf(movie.getCoordinates().getY()));
            writer.writeEndElement();

            writeElement(writer, "creationDate", movie.getCreationDate().toString());
            writeElement(writer, "oscarsCount", String.valueOf(movie.getOscarsCount()));

            if (movie.getGenre() != null) {
                writeElement(writer, "genre", movie.getGenre().name());
            }

            if (movie.getMpaaRating() != null) {
                writeElement(writer, "mpaaRating", movie.getMpaaRating().name());
            }

            if (movie.getScreenwriter() != null) {
                writer.writeStartElement("screenwriter");
                writeElement(writer, "name", movie.getScreenwriter().getName());
                writeElement(writer, "passportID", movie.getScreenwriter().getPassportID());
                writeElement(writer, "height", String.valueOf(movie.getScreenwriter().getHeight()));

                if (movie.getScreenwriter().getHairColor() != null) {
                    writeElement(writer, "hairColor", movie.getScreenwriter().getHairColor().name());
                }
                writer.writeEndElement();
            }
            writer.writeEndElement();
        } catch (Exception e) {
            logger.error("Ошибка при записи фильма с ID " + movie.getId() + ": " + e.getMessage());
            throw e;
        }
    }

    private void writeElement(XMLStreamWriter writer, String name, String value) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(value);
        writer.writeEndElement();
    }

    public HashSet<Movie> readCollection() {
        if (fileName == null || fileName.isEmpty()) {
            logger.error("Имя файла не указано");
            return new HashSet<>();
        }

        logger.info("Начало загрузки коллекции из файла: " + fileName);
        HashSet<Movie> collection = new HashSet<>();
        int successCount = 0;

        try {
            // Читаем весь файл в строку
            String xmlContent = new String(Files.readAllBytes(Paths.get(fileName)));

            // Разбиваем по тегам <movie> и </movie>
            String[] movieParts = xmlContent.split("<movie>|</movie>");

            for (String movieXml : movieParts) {
                if (movieXml.trim().isEmpty()) continue;

                try {
                    Movie movie = parseMovie("<movie>" + movieXml + "</movie>");
                    if (movie != null && movie.validate()) {
                        collection.add(movie);
                        successCount++;
                    }
                } catch (Exception e) { }
            }

            if (successCount > 0) {
                logger.info("Успешно загружено " + successCount + " фильмов");
            }

            if (successCount == 0) {
                logger.info("Файл не содержит данных о фильмах");
            }

        } catch (FileNotFoundException e) {
            logger.error("Файл не найден: " + fileName);
        } catch (Exception e) {
            logger.error("Критическая ошибка при чтении файла: " + e.getMessage());
        }

        return collection;
    }

    private Movie parseMovie(String movieXml) {
        Movie movie = new Movie(0, null, null, 0, null, null, null);
        Person screenwriter = null;
        Coordinates coordinates = null;

        try {
            // Извлекаем id
            movie.setId(Integer.parseInt(extractTagValue(movieXml, "id")));

            // Извлекаем name
            movie.setName(extractTagValue(movieXml, "name"));

            // Извлекаем coordinates
            String coordXml = extractTagXml(movieXml, "coordinates");
            if (coordXml != null) {
                coordinates = new Coordinates(
                        Integer.parseInt(extractTagValue(coordXml, "x")),
                        Float.parseFloat(extractTagValue(coordXml, "y"))
                );
                movie.setCoordinates(coordinates);
            }

            // Извлекаем creationDate
            movie.setCreationDate(LocalDateTime.parse(extractTagValue(movieXml, "creationDate")));

            // Извлекаем oscarsCount
            movie.setOscarsCount(Long.parseLong(extractTagValue(movieXml, "oscarsCount")));

            // Извлекаем genre
            String genreValue = extractTagValue(movieXml, "genre");
            if (!genreValue.isEmpty()) {
                movie.setGenre(MovieGenre.valueOf(genreValue));
            }

            // Извлекаем mpaaRating
            String ratingValue = extractTagValue(movieXml, "mpaaRating");
            if (!ratingValue.isEmpty()) {
                movie.setMpaaRating(MpaaRating.valueOf(ratingValue));
            }

            // Извлекаем screenwriter
            String screenwriterXml = extractTagXml(movieXml, "screenwriter");
            if (screenwriterXml != null) {
                screenwriter = new Person(
                        extractTagValue(screenwriterXml, "name"),
                        null, // birthday
                        Double.parseDouble(extractTagValue(screenwriterXml, "height")),
                        extractTagValue(screenwriterXml, "passportID"),
                        Color.valueOf(extractTagValue(screenwriterXml, "hairColor"))
                );
                movie.setScreenwriter(screenwriter);
            }

            return movie;
        } catch (Exception e) {
            return null;
        }
    }

    // Вспомогательный метод для извлечения значения тега
    private String extractTagValue(String xml, String tagName) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";

        int start = xml.indexOf(openTag);
        if (start == -1) return "";

        start += openTag.length();
        int end = xml.indexOf(closeTag, start);
        if (end == -1) return "";

        return xml.substring(start, end);
    }

    // Вспомогательный метод для извлечения XML блока тега
    private String extractTagXml(String xml, String tagName) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";

        int start = xml.indexOf(openTag);
        if (start == -1) return null;

        int end = xml.indexOf(closeTag, start);
        if (end == -1) return null;

        return xml.substring(start, end + closeTag.length());
    }
}