package fr.foacs.hostocars;

import fr.foacs.hostocars.configuration.StartupWorker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application launcher with arguments.
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "HostoCars API", version = "2.0.1-SNAPSHOT"))
public class Application {

    /**
     * Main function with arguments.
     *
     * @param args
     *     The execution arguments
     */
    public static void main(final String[] args) {
        // Performs the pre-run initialization
        StartupWorker.initialize();

        // Runs the application
        SpringApplication.run(Application.class, args);
    }

}
