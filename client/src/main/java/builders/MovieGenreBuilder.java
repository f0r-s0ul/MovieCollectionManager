package builders;

import console.Console;
import exceptions.IncorrectInputInScriptException;
import structure.MovieGenre;
import utilities.ReadModeManager;

import java.util.NoSuchElementException;

public class MovieGenreBuilder extends AbstractBuilder<MovieGenre> {
    private final Console console;

    public MovieGenreBuilder(Console console) {
        this.console = console;
    }

    @Override
    public MovieGenre build() throws IncorrectInputInScriptException {
        var fileMode = ReadModeManager.getFileMode();

        String strMovieGenre;
        MovieGenre genre;
        while (true) {
            try {
                console.println("Введите жанр или номер жанра от 1 до " + MovieGenre.lastIndex() + " (или null):");
                console.println("Список жанров - " + MovieGenre.names());
                console.ps2();

                strMovieGenre = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strMovieGenre);

                try {
                    int id = Integer.parseInt(strMovieGenre);
                    genre = MovieGenre.byID(id);
                    break;
                } catch (Exception e) {
                    if (strMovieGenre.equals("") || strMovieGenre.equals("null")) return null;
                    genre = MovieGenre.valueOf(strMovieGenre.toUpperCase());
                    break;
                }
            } catch (NoSuchElementException exception) {
                console.printError("Жанр не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Жанра нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return genre;
    }
}
