package builders;

import console.Console;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.NotInDeclaredLimitsException;
import structure.Coordinates;
import utilities.ReadModeManager;

import java.util.NoSuchElementException;

public class CoordinatesBuilder extends AbstractBuilder<Coordinates> {
    private final Console console;

    public CoordinatesBuilder(Console console) {
        this.console = console;
    }

    @Override
    public Coordinates build() throws IncorrectInputInScriptException, InvalidFormException {
        console.println("! Внесение данных о координатах места съемки:");

        var coordinates = new Coordinates(askX(), askY());
        if (!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    public Integer askX() throws IncorrectInputInScriptException {
        var fileMode = ReadModeManager.getFileMode();
        int x;
        while (true) {
            try {
                console.println("Введите координату X:");
                console.ps2();
                var strX = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strX);

                x = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена целым числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    public Float askY() throws IncorrectInputInScriptException {
        var fileMode = ReadModeManager.getFileMode();
        float y;

        while (true) {
            try {
                console.println("Введите координату Y (формата y.yyy):");
                console.ps2();
                var strY = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strY);

                y = Float.parseFloat(strY);
                if (y > 791.0f) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Значение Y не должно превышать 791!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }
}