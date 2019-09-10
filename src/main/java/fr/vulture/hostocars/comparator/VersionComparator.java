package fr.vulture.hostocars.comparator;

import java.util.Comparator;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * Custom comparator for versions.
 */
@Component("versionComparator")
public class VersionComparator implements Comparator<String> {

    /**
     * Regex for versions.
     */
    public static final String VERSION_STRING_REGEX = "[0-9]+\\.[0-9]+\\.[0-9]+";

    @Override
    public int compare(@NotNull final String o1, @NotNull final String o2) {
        // Checks that both arguments are versions
        if (!o1.matches(VERSION_STRING_REGEX) || !o2.matches(VERSION_STRING_REGEX)) {
            throw new IllegalArgumentException("The compared arguments have to be versions");
        }

        // Splits the versions
        final String[] versionNumbers1 = o1.split("\\.");
        final String[] versionNumbers2 = o2.split("\\.");

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
