package builders;

import console.Console;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;
import structure.*;
import utilities.ReadModeManager;

import java.util.NoSuchElementException;

public class MovieBuilder extends AbstractBuilder<Movie> {
    private final Console console;

    public MovieBuilder(Console console) {
        this.console = console;
    }

    @Override
    public Movie build() throws IncorrectInputInScriptException, InvalidFormException {
        console.println("! Внесение данных о фильме");

        var movie = new Movie(
                1,
                askName(),
                askCoordinates(),
                askOscarsCount(),
                askGenre(),
                askMpaaRating(),
                askScreenwriter()
        );
        if (!movie.validate()) throw new InvalidFormException();
        return movie;
    }

    private String askName() throws IncorrectInputInScriptException {
        String name;
        var fileMode = ReadModeManager.getFileMode();
        while (true) {
            try {
                console.println("Введите название фильма:");
                console.ps2();

                name = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private Coordinates askCoordinates() throws IncorrectInputInScriptException, InvalidFormException {
        return new CoordinatesBuilder(console).build();
    }

    private Long askOscarsCount() throws IncorrectInputInScriptException {
        Long oscarsCount;
        var fileMode = ReadModeManager.getFileMode();
        while (true) {
            try {
                console.println("Введите количество оскаров:");
                console.ps2();

                var strOscarsCount = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strOscarsCount);

                oscarsCount = Long.parseLong(strOscarsCount);
                if (oscarsCount < 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Количесвто оскаров не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Количесвто оскаров должно быть представлено целым числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Количество оскаров должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return oscarsCount;
    }

    private MovieGenre askGenre() throws IncorrectInputInScriptException {
        return new MovieGenreBuilder(console).build();
    }

    private MpaaRating askMpaaRating() throws IncorrectInputInScriptException {
        return new MpaaRatingBuilder(console).build();
    }

    private Person askScreenwriter() throws IncorrectInputInScriptException, InvalidFormException {
        return new PersonBuilder(console).build();
    }
}
