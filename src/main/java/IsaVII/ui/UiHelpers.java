package IsaVII.ui;

public class UiHelpers {

    public static final String ITALIC = "\u001B[3m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String RESET = "\u001B[0m";
    
    public static String italic(String text) {
        return ITALIC + text + RESET;
    }
    
    public static String bold(String text) {
        return BOLD + text + RESET;
    }
    
    public static String underline(String text) {
        return UNDERLINE + text + RESET;
    }

    public static void printLine() {
        IO.println("===================");
    }
}
