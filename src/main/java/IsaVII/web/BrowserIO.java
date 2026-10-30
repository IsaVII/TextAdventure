package IsaVII.web;

// Native methods implemented in JavaScript under web/index.html, following CheerpJ's
// Java_<fqcn>_<method> binding convention. jsReadLine blocks the Java thread until the
// JS side resolves its promise (e.g. the player submits the input box), so Scanner on top
// of this behaves exactly like a real terminal.
public class BrowserIO {

    public static native String jsReadLine();

    public static native void jsWrite(String text);
}
