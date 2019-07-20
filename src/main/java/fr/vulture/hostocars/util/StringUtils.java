package fr.vulture.hostocars.util;

import java.util.Comparator;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to manipulate Strings.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static final String VERSION_STRING_REGEX = "[0-9]+\\.[0-9]+\\.[0-9]+";

    public static final Comparator<String> versionComparator = StringUtils::compareVersions;

    /**
     * Compares 2 versions as strings. Returns a negative integer, zero, or a positive integer as the first version is less than, equal to, or greater
     * than the second.
     *
     * @param version1
     *     The first version to be compared
     * @param version2
     *     The second version to be compared
     *
     * @return a negative integer, zero, or a positive integer as the first version is less than, equal to, or greater than the second
     */
    private static int compareVersions(@NotNull final String version1, @NotNull final String version2) {
        // Checks that the first argument is a version
        if (!version1.matches(VERSION_STRING_REGEX)) {
            throw new IllegalStateException("First argument is not a version");
        }

        // Checks that the second argument is a version
        if (!version2.matches(VERSION_STRING_REGEX)) {
            throw new IllegalStateException("Second argument is not a version");
        }

        // Splits the versions
        final String[] versionNumbers1 = version1.split("\\.");
        final String[] versionNumbers2 = version2.split("\\.");

        // Gets the major versions of both arguments
        final Integer majorVersion1 = Integer.valueOf(versionNumbers1[0]);
        final Integer majorVersion2 = Integer.valueOf(versionNumbers2[0]);

        // Compares the major versions of both arguments
        final int majorVersionsComparison = majorVersion1.compareTo(majorVersion2);

        // If the major versions are different, returns their comparison result
        if (majorVersionsComparison != 0) {
            return majorVersionsComparison;
        }

        // Gets the medium versions of both arguments
        final Integer mediumVersion1 = Integer.valueOf(versionNumbers1[1]);
        final Integer mediumVersion2 = Integer.valueOf(versionNumbers2[1]);

        // Compares the medium versions of both arguments
        final int mediumVersionsComparison = mediumVersion1.compareTo(mediumVersion2);

        // If the medium versions are different, returns their comparison result
        if (mediumVersionsComparison != 0) {
            return mediumVersionsComparison;
        }

        // Gets the minor versions of both arguments
        final Integer minorVersion1 = Integer.valueOf(versionNumbers1[2]);
        final Integer minorVersion2 = Integer.valueOf(versionNumbers2[2]);

        // Returns the result of the comparison between the minor versions
        return minorVersion1.compareTo(minorVersion2);
    }

}
