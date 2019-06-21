package fr.vulture.hostocars.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * Utility class to manipulate objects.
 */
public class ObjectUtils {

    /**
     * Default constructor.
     */
    private ObjectUtils() {
        super();
    }

    /**
     * Returns true if any input object is null.
     *
     * @param objects
     *     The objects
     *
     * @return true if any input object is null
     */
    public static boolean isAnyNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }

    /**
     * Returns true if any input object is not null.
     *
     * @param objects
     *     The objects
     *
     * @return true if any input object is not null
     */
    public static boolean isAnyNonNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::nonNull);
    }

}
