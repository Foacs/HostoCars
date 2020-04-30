package fr.vulture.hostocars.utils;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;

/**
 * Utility class exposing constants and methods for controllers.
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class ControllerUtils {

    /**
     * The default {@link ExampleMatcher} to use for searches.
     */
    public static final ExampleMatcher DEFAULT_MATCHER = matchingAll().withIgnoreCase().withStringMatcher(CONTAINING);

}
