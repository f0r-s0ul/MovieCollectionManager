package console;

import console.interfaces.IConsole;

public class Console implements IConsole {
    private static final String PS1 = "$ ";
    private static final String PS2 = "> ";

    @Override
    public void print(Object o) {
        System.out.print(o.toString());
    }

    @Override
    public void println(Object o) {
        System.out.println(o.toString());
    }

    @Override
    public void printError(Object o) {
        System.out.println("Error: " + o.toString());
    }

    @Override
    public void printTable(Object o1, Object o2) {
        System.out.printf(" %-35s%-1s%n", o1, o2);
    }

    @Override
    public void ps1() {
        print(PS1);
    }

    @Override
    public void ps2() {
        print(PS1);
    }

    @Override
    public String getPS1() {
        return PS1;
    }

    @Override
    public String getPS2() {
        return PS1;
    }
}
