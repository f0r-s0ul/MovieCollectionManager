package builders;

import console.Console;
import exceptions.IncorrectInputInScriptException;
import structure.Color;
import utilities.ReadModeManager;

import java.util.NoSuchElementException;

public class ColorBuilder extends AbstractBuilder<Color> {
    private final Console console;

    public ColorBuilder(Console console) {
        this.console = console;
    }

    @Override
    public Color build() throws IncorrectInputInScriptException {
        var fileMode = ReadModeManager.getFileMode();

        String strColor;
        Color color;
        while (true) {
            try {
                console.println("Введите цвет волос или номер цвета от 1 до " + Color.lastIndex() + " (или null):");
                console.println("Список цветов - " + Color.names());
                strColor = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strColor);

                try {
                    int id = Integer.parseInt(strColor);
                    color = Color.byID(id);
                    break;
                } catch(Exception e) {
                    if (strColor.equals("") || strColor.equals("null")) return null;
                    color = Color.valueOf(strColor.toUpperCase());
                    break;
                }
            } catch (NoSuchElementException exception) {
                console.printError("Цвет не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }
}
