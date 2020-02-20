package fr.vulture.hostocars;

import fr.vulture.hostocars.system.SystemTrayHelper;
import java.awt.SystemTray;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Application launcher with arguments.
 */
@Slf4j
@SpringBootApplication
public class Application {

    /**
     * Main function with arguments.
     *
     * @param args
     *     The execution arguments
     */
    public static void main(final String[] args) {
        // Defines and starts the application context
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).run();

        // If the system tray is supported, adds the application tray icon
        if (SystemTray.isSupported()) {
            SwingUtilities.invokeLater(() -> SystemTrayHelper.addTrayIcon(context));
        } else {
            log.warn("System tray not supported on this platform");
        }
    }

}
