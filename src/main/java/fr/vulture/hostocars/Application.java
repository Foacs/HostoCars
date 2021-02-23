package fr.vulture.hostocars;

import static org.springframework.boot.SpringApplication.run;

import fr.vulture.hostocars.configuration.Loggable;
import fr.vulture.hostocars.configuration.StartupWorker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application launcher with arguments.
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "HostoCars API", version = "2.0.0"))
public class Application {

    /**
     * Main function with arguments.
     *
     * @param args
     *     The execution arguments
     */
    @Loggable
    public static void main(final String[] args) {
        // Performs the pre-run initialization
        StartupWorker.initialize(args);

        // Runs the application
        run(Application.class, args);
    }

}
