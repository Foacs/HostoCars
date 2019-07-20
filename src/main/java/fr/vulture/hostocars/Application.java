package fr.vulture.hostocars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application launcher with arguments.
 */
@SpringBootApplication
public class Application {

    /**
     * Main function with arguments.
     *
     * @param args
     *     The execution arguments
     */
    public static void main(final String[] args) {
        // Starts the Spring application
        SpringApplication.run(Application.class, args);
    }

}
