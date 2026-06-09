package scintilla;

public class Environment {
    private static final String PREFIX = "scintilla:";

    private volatile static String IPAddress = null;
    private volatile static Integer Port = null;
    private volatile static Boolean IsHeadless = null;
    private volatile static Boolean IsSecure = null;

    public synchronized static String getIPAddress() {
        return (IPAddress == null && (IPAddress = System.getenv(PREFIX + "ADDRESS")) == null)
                ? IPAddress = "localhost" // default to localhost
                : IPAddress;
    }

    public synchronized static int getPort() {
        if (Port != null) return Port;
        try {
            return Port = Integer.parseInt(System.getenv(PREFIX + "PORT"));
        } catch (Exception e) {
            return Port = 4568;
        }
    }

    public synchronized static boolean isHeadless() {
        return IsHeadless != null ? IsHeadless.booleanValue() : (IsHeadless = (System.getenv(PREFIX + "HEADLESS") != null));
    }

    public synchronized static boolean isSecure() {
        return IsSecure != null ? IsSecure.booleanValue() : (IsSecure = (System.getenv(PREFIX + "SECURE") != null));
    }
}
