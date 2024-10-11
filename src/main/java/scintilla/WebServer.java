package scintilla;

import static spark.Spark.*;

/**
 * Package only webserver, as to not expose it outside this package.
 */
final class WebServer {
    private final String ipAddress = Environment.getIPAddress();
    private final int port = Environment.getPort();
    private final boolean isSecure = Environment.isSecure();

    public void initialize() {
        port(port);
        ipAddress(ipAddress);

        staticFiles.location("/");
        staticFiles.header("Access-Control-Allow-Origin", "*");
        staticFiles.header("Access-Control-Allow-Methods", "*");
        staticFiles.header("Access-Control-Allow-Headers", "*");

        initExceptionHandler((e) -> {
            System.err.println("Exception " + e.getMessage() + " was raised");
            e.printStackTrace(System.err);
        });
    }

    protected void finalize() {
        awaitInitialization();
    }

    public String getHostUrl() {
        return (isSecure ? "https://" : "http://") + ipAddress + ":" + port;
    }
}
