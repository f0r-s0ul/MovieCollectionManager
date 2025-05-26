package builders;

import console.Console;
import exceptions.*;
import structure.Color;
import structure.Person;
import utilities.ReadModeManager;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

public class PersonBuilder extends AbstractBuilder<Person> {
    private final Console console;

    public PersonBuilder(Console console) {
        this.console = console;
    }

    @Override
    public Person build() throws IncorrectInputInScriptException, InvalidFormException {
        console.println("Для пропуска ввода режисера введите null, для создания нового введите new");
        console.ps2();

        var fileMode = ReadModeManager.getFileMode();
        String input = ReadModeManager.getUserScanner().nextLine().trim();
        if (fileMode) console.println(input);
        if (input.equals("null")) return null;

        console.println("! Внесение данных о новом режисере:");
        var screenwriter = new Person(
                askName(),
                askBirthday(),
                askHeight(),
                askPassportID(),
                askHairColor()
        );
        if (!screenwriter.validate()) throw new InvalidFormException();
        return screenwriter;
    }

    private String askName() throws IncorrectInputInScriptException {
        String name;
        var fileMode = ReadModeManager.getFileMode();
        while (true) {
            try {
                console.println("Введите имя режисера:");
                console.ps2();

                name = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Имя режисера не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Имя режисера не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private ZonedDateTime askBirthday() throws IncorrectInputInScriptException {
        return null;
    }

    private Double askHeight() throws IncorrectInputInScriptException {
        double height;
        var fileMode = ReadModeManager.getFileMode();
        while (true) {
            try {
                console.println("Введите рост:");
                console.ps2();

                var strHeight = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strHeight);

                height = Double.parseDouble(strHeight);
                if (height <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Рост не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Рост должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Рост должен быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return height;
    }

    private String askPassportID() throws IncorrectInputInScriptException, InvalidFormException {
        String passportID;
        var fileMode = ReadModeManager.getFileMode();
        while (true) {
            try {
                console.println("Введите id паспорта режисера:");
                console.ps2();

                passportID = ReadModeManager.getUserScanner().nextLine().trim();
                if (fileMode) console.println(passportID);
                if (passportID.isEmpty()) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("ID паспорта не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("ID паспорта не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }  catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return passportID;
    }

    private Color askHairColor() throws IncorrectInputInScriptException {
        return new ColorBuilder(console).build();
    }
}