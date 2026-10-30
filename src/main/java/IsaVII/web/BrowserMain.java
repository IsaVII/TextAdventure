package IsaVII.web;

import IsaVII.AdventureApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

// Entry point used only when running under CheerpJ in the browser (see web/index.html).
// It redirects System.in/System.out to the JS bridge in BrowserIO, then runs the exact
// same game as the desktop build (AdventureApp), unmodified.
public class BrowserMain {

    public static void main(String[] args) {
        System.setOut(new PrintStream(new JsOutputStream(), true, StandardCharsets.UTF_8));
        System.setIn(new JsInputStream());

        AdventureApp.main(args);
    }

    private static class JsOutputStream extends OutputStream {
        private final StringBuilder lineBuffer = new StringBuilder();

        @Override
        public void write(int b) {
            if (b == '\n') {
                BrowserIO.jsWrite(lineBuffer.toString());
                lineBuffer.setLength(0);
            } else if (b != '\r') {
                lineBuffer.append((char) b);
            }
        }
    }

    private static class JsInputStream extends InputStream {
        private byte[] currentLine = new byte[0];
        private int position = 0;

        @Override
        public int read() throws IOException {
            if (position >= currentLine.length) {
                String line = BrowserIO.jsReadLine();
                if (line == null) {
                    return -1;
                }
                currentLine = (line + "\n").getBytes(StandardCharsets.UTF_8);
                position = 0;
            }
            return currentLine[position++] & 0xFF;
        }
    }
}
