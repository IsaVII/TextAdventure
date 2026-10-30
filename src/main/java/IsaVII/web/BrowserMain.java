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
        // Raw diagnostic breadcrumb, bypassing System.out entirely: if this line never
        // shows up in the terminal, the Java<->JS native bridge itself isn't resolving,
        // as opposed to a failure somewhere later in the game's own startup logic.
        BrowserIO.jsWrite("[BrowserMain] native bridge reachable, starting game...");

        PrintStream bridgeOut = new PrintStream(new JsOutputStream(), true, StandardCharsets.UTF_8);
        System.setOut(bridgeOut);
        System.setErr(bridgeOut);
        System.setIn(new JsInputStream());

        try {
            AdventureApp.main(args);
        } catch (Throwable t) {
            t.printStackTrace();
            BrowserIO.jsWrite("[BrowserMain] " + t);
            throw t;
        }
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
            byte[] single = new byte[1];
            int read = read(single, 0, 1);
            return read == -1 ? -1 : (single[0] & 0xFF);
        }

        // Scanner/InputStreamReader don't read one byte at a time - they ask to bulk-fill a
        // buffer of hundreds/thousands of bytes. InputStream.read(byte[],off,len) is allowed
        // to return fewer bytes than requested, so this returns as soon as the current
        // buffered line is exhausted instead of blocking on a second jsReadLine() call to
        // keep filling the caller's buffer (which would silently eat the player's next line).
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (len == 0) {
                return 0;
            }
            if (position >= currentLine.length) {
                String line = BrowserIO.jsReadLine();
                if (line == null) {
                    return -1;
                }
                currentLine = (line + "\n").getBytes(StandardCharsets.UTF_8);
                position = 0;
            }
            int toCopy = Math.min(currentLine.length - position, len);
            System.arraycopy(currentLine, position, b, off, toCopy);
            position += toCopy;
            return toCopy;
        }
    }
}
