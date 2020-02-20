package fr.vulture.hostocars.system;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Helper to add a tray icon at application startup.
 */
@Slf4j
@Component
public final class SystemTrayHelper {

    private static ResourceExtractor resourceExtractor;

    static {
        // Sets the java.awt.headless system property to enable the tray icon
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Valued autowired constructor.
     *
     * @param resourceExtractor
     *     The autowired {@link ResourceExtractor} component
     */
    @Autowired
    public SystemTrayHelper(final ResourceExtractor resourceExtractor) {
        SystemTrayHelper.resourceExtractor = resourceExtractor;
    }

    /**
     * Adds an application tray icon to the system tray.
     *
     * @param context
     *     The application context
     */
    public static void addTrayIcon(final ApplicationContext context) {
        try {
            // Gets the system tray factory
            final SystemTray tray = SystemTray.getSystemTray();

            // Extracts the URL of the tray icon resource file
            final URL resource = resourceExtractor.extractIcon();

            // Creates the tray icon with the icon
            final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(resource), "HostoCars");

            // Generates the application URI
            final URI applicationUri = new URI("http://localhost:8080/");

            // Adds an event listener to open a tab on the default browser with the application URI
            trayIcon.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(applicationUri);
                } catch (final IOException exception) {
                    log.error("Failed to open new tab in default browser", exception);
                }
            });

            // Creates a menu item for the tray icon in order to exit the application
            final MenuItem exitItem = new MenuItem("Quitter");

            // Adds an event listener to the exit menu item to exit the application and remove the tray icon
            exitItem.addActionListener(e -> {
                tray.remove(trayIcon);
                SpringApplication.exit(context, () -> 0);
            });

            // Creates a popup menu for the tray icon
            final PopupMenu popupMenu = new PopupMenu();

            // Adds the exit menu item to the popup menu
            popupMenu.add(exitItem);

            // Adds the popup menu to the tray icon
            trayIcon.setPopupMenu(popupMenu);

            // Adds the tray icon to the system tray
            tray.add(trayIcon);
        } catch (final AWTException | IOException | URISyntaxException exception) {
            log.error("Tray icon could not to be added", exception);
        }
    }

}
