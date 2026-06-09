package scintilla;

public class Scintilla {
    private static final WebServer INSTANCE = new WebServer();

    // block initialisation of multiple WebServers
    private Scintilla() {
    }

    public static void initialize() {
        INSTANCE.initialize();
    }

    public static void start() {
        INSTANCE.awaitReady();
        PlatformUtils.openBrowserAtPath(INSTANCE.getHostUrl() + "/app/");
    }
}
