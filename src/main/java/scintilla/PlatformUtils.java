package scintilla;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

final class PlatformUtils {
    /**
     * Opens the user's specified browser at a given path
     *
     * @param path The path (with protocol + host included) to open to.
     */
    public static boolean openBrowserAtPath(String path) {
        // A way you could approach this is java.awt but that typically requires as many steps as this
        // and is pretty deprecated these days, this is just simpler and will be more crossplatform

        // Since students can call this directly we need to ensure we aren't running in headless
        if (Environment.isHeadless()) {
            System.err.println("Running in HEADLESS mode, denying Browser from opening.");
            return false;
        }

        List<String> command;

        // Determine what command to run based on if we are Windows/Mac/Linux
        switch (determineOperatingSystem()) {
            case MacOS:
                command = Arrays.asList("open", path);
                break;
            case Windows:
                command = Arrays.asList("rundll32", "url.dll,FileProtocolHandler", path);
                break;
            case Unknown:
            default:
            case Linux:
                //  if it's an unknown file system we'll just open it as linux since it's most likely something like Solaris
                command = Arrays.asList("xdg-open", path);
                break;
        }

        try {
            // Build command safely so paths/URLs with spaces are handled correctly.
            ProcessBuilder processBuilder = new ProcessBuilder(new ArrayList<>(command));
            processBuilder.start();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to open browser (" + String.join(" ", command) + ")");
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * Determine what operating system the user has.
     *
     * @return Either MacOs/Windows/Linux or Unknown if can't determine the OS.
     */
    public static OperatingSystemType determineOperatingSystem() {
        String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (osName.contains("mac") || osName.contains("darwin")) {
            return OperatingSystemType.MacOS;
        } else if (osName.contains("win")) {
            return OperatingSystemType.Windows;
        } else if (osName.contains("nux") || osName.contains("nix") || osName.contains("aix")) {
            return OperatingSystemType.Linux;
        } else {
            System.err.println("Unknown/Unsupported Operating System... " + osName);
            return OperatingSystemType.Unknown;
        }
    }

    /**
     * Operating System's we support.
     */
    public enum OperatingSystemType {
        Windows, MacOS, Linux, Unknown
    }
}
