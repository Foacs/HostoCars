package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.configuration.Loggable;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * Helper for controllers.
 */
@Controller
class ControllerHelper {

    @NonNull
    @Value("${server.address}")
    private String serverAddress;

    @NonNull
    @Value("${server.port}")
    private String serverPort;

    /**
     * Resolves a {@link HttpMethod#GET} method response from an {@link Optional} result.
     *
     * @param executable
     *     The executable to perform
     * @param <T>
     *     The type of the result
     *
     * @return an {@link HttpStatus#OK} response if the result is present, else a {@link HttpStatus#NOT_FOUND} response
     */
    @Loggable(debug = true)
    @SneakyThrows
    <T> ResponseEntity<T> resolveGetResponse(final Callable<Optional<T>> executable) {
        return ResponseEntity.of(executable.call());
    }

    /**
     * Resolves a {@link HttpMethod#GET} method response from a {@link Collection} of results.
     *
     * @param executable
     *     The executable to perform
     * @param <T>
     *     The type of the result
     *
     * @return an {@link HttpStatus#OK} response
     */
    @Loggable(debug = true)
    @SneakyThrows
    <T> ResponseEntity<Collection<T>> resolveGetCollectionResponse(final Callable<? extends Collection<T>> executable) {
        final Collection<T> result = executable.call();
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    /**
     * Resolves a {@link HttpMethod#POST} method response with a given location.
     *
     * @param executable
     *     The executable to perform
     *
     * @return an {@link HttpStatus#CREATED} response
     */
    @Loggable(debug = true)
    @SneakyThrows
    ResponseEntity resolvePostResponse(final Callable<String> executable) {
        return ResponseEntity.created(new URI("http://" + this.serverAddress + ':' + this.serverPort + executable.call())).build();
    }

    /**
     * Resolves a {@link HttpMethod#PUT} method response.
     *
     * @param executable
     *     The executable to perform
     *
     * @return an {@link HttpStatus#NO_CONTENT} response
     */
    @Loggable(debug = true)
    @SneakyThrows
    ResponseEntity resolvePutResponse(final Runnable executable) {
        executable.run();
        return ResponseEntity.noContent().build();
    }

    /**
     * Resolves a {@link HttpMethod#DELETE} method response.
     *
     * @param executable
     *     The executable to perform
     *
     * @return an {@link HttpStatus#NO_CONTENT} response
     */
    @Loggable(debug = true)
    @SneakyThrows
    ResponseEntity resolveDeleteResponse(final Runnable executable) {
        executable.run();
        return ResponseEntity.noContent().build();
    }

    /**
     * {@link RestTemplate} bean for the {@link} component.
     *
     * @return a {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
