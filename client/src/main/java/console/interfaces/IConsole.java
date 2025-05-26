package console.interfaces;

public interface IConsole {
    void print(Object o);
    void println(Object o);
    void printError(Object o);
    void printTable(Object o1, Object o2);
    void ps1();
    void ps2();
    String getPS1();
    String getPS2();
}
