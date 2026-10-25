package IsaVII.ui;

public class UiHelpers {

    public static final String ITALIC = "\u001B[3m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String RESET = "\u001B[0m";
    
    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String MAGENTA = "\u001B[95m";
    public static final String CYAN = "\u001B[96m";
    public static final String GREY = "\u001B[38;5;245m";
    public static final String TEST = "\u001B[38;5;68m";
    
    public static String italic(String text) {
        return ITALIC + text + RESET;
    }

    public static String color(String text, String colorCode) {
        return colorCode + text + RESET;
    }
    
    public static String bold(String text) {
        return BOLD + text + RESET;
    }
    
    public static String underline(String text) {
        return UNDERLINE + text + RESET;
    }

    public static void printLine(boolean thin) {
        if (thin) {
            IO.println("-------------------");
        } else {
            IO.println("===================");
        }
    }
}
