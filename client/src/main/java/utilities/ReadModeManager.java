package utilities;

import java.util.Scanner;


public class ReadModeManager {
    private static Scanner userScanner;
    private static boolean readFileMode = false;

    public static Scanner getUserScanner() {
        return userScanner;
    }
    public static void setUserScanner(Scanner userScanner) {
        ReadModeManager.userScanner = userScanner;
    }

    public static boolean getFileMode() {
        return readFileMode;
    }
    public static void setUserMode() {
        ReadModeManager.readFileMode = false;
    }
    public static void setFileMode() {
        ReadModeManager.readFileMode = true;
    }
}
