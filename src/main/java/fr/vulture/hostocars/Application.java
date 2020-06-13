package fr.vulture.hostocars;

import static java.util.Arrays.asList;
import static org.springframework.boot.SpringApplication.run;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application launcher with arguments.
 */
@Slf4j
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "HostoCars API", version = "2.0"))
public class Application {

    /**
     * Main function with arguments.
     *
     * @param args
     *     The execution arguments
     */
    public static void main(final String[] args) {
        // Defines and starts the application context
        run(Application.class, args);

        log.debug("Started application with args : {}", asList(args));
    }

}
