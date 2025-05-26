package builders;

import console.Console;
import exceptions.IncorrectInputInScriptException;
import structure.MpaaRating;
import utilities.ReadModeManager;

import java.util.NoSuchElementException;

public class MpaaRatingBuilder extends AbstractBuilder<MpaaRating> {
    private final Console console;

    public MpaaRatingBuilder(Console console) {
        this.console = console;
    }

    @Override
    public MpaaRating build() throws IncorrectInputInScriptException {
        var fileMode = ReadModeManager.getFileMode();

        String strMpaaRating;
        MpaaRating rating;
        while (true) {
            try {
                console.println("Введите рейтинг или номер рейтиннга от 1 до " + MpaaRating.lastIndex() + " (или null):");
                console.println("Список MPAA рейтингов - " + MpaaRating.names());
                console.ps2();

                strMpaaRating = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strMpaaRating);

                try {
                    int id = Integer.parseInt(strMpaaRating);
                    rating = MpaaRating.byID(id);
                    break;
                } catch (Exception e) {
                    if (strMpaaRating.equals("") || strMpaaRating.equals("null")) return null;
                    rating = MpaaRating.valueOf(strMpaaRating.toUpperCase());
                    break;
                }
            } catch (NoSuchElementException exception) {
                console.printError("Рейтинг не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Рейтинга нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return rating;
    }
}