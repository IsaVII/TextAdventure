package IsaVII.ui;

import java.util.Scanner;

// Stands in for the java.io.IO preview class so the project can compile without preview
// features (java.io.IO's class files can't run on CheerpJ's browser JVM). System.out/System.in
// are swapped per entry point (see IsaVII.web.BrowserMain), so this works unchanged everywhere.
public final class IO {

    private static final Scanner SCANNER = new Scanner(System.in);

    private IO() {
    }

    public static void println(Object value) {
        System.out.println(value);
    }

    public static void println() {
        System.out.println();
    }

    public static String readln(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }
}
