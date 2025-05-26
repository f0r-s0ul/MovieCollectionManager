import console.Console;
import network.UDPClient;
import utilities.Runner;

import java.io.IOException;
import java.net.InetAddress;

public class Client {
    private static int PORT;

    public static void main(String[] args) {
        var console = new Console();

        try {
            PORT = Integer.parseInt(args[0]);
            if (PORT < 32000 || PORT > 64000) {
                console.printError("Порт должен принимать значение в диапозоне от 32000 до 64000! (аргумент запуска 1)");
                System.exit(1);
            }
        } catch (Exception e) {
            console.printError("Значение порта должно быть числом в диапозоне от 32000 до 64000! (аргумент запуска 1)");
            System.exit(1);
        }

        try {
            var client = new UDPClient(InetAddress.getLocalHost(), PORT);
            var runner = new Runner(client, console);

            runner.interactiveMode();
        } catch (IOException e) {
            console.printError("Невозможно подключиться к серверу!");
        }
    }
}