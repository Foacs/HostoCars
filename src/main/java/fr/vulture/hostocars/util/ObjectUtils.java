package fr.vulture.hostocars.util;

import static java.util.Objects.isNull;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to manipulate objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtils {

    /**
     * If the {@code all} parameter is on, returns true if all the inputs objects are null; else, return true if any of them is null.
     *
     * @param all
     *     If all of the input objects have to be null to return true
     * @param objects
     *     The objects
     *
     * @return true if any or all of the input objects are null depending on the {@code all} parameter
     */
    public static boolean areNull(final boolean all, @NotNull final Object... objects) {
        if (isNull(objects) || objects.length == 0) {
            throw new InvalidParameterException("No object to check");
        }

        return all ? !areNonNull(false, objects) : Arrays.stream(objects).anyMatch(Objects::isNull);
    }

    /**
     * If the {@code all} parameter is on, returns true if all the inputs objects are null; else, return true if any of them is null.
     *
     * @param all
     *     If all of the input objects have to be null to return true
     * @param objects
     *     The objects
     *
     * @return true if any or all of the input objects are null depending on the {@code all} parameter
     */
    public static boolean areNonNull(final boolean all, @NotNull final Object... objects) {
        if (isNull(objects) || objects.length == 0) {
            throw new InvalidParameterException("No object to check");
        }

        return all ? !areNull(false, objects) : Arrays.stream(objects).anyMatch(Objects::nonNull);
    }

}
