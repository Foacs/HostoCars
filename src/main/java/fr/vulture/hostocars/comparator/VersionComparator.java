package fr.vulture.hostocars.comparator;

import java.io.Serializable;
import java.util.Comparator;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * Custom comparator for versions.
 */
@Component("versionComparator")
public class VersionComparator implements Comparator<String>, Serializable {

    private static final long serialVersionUID = -657647773545405079L;

    /**
     * Regex for versions.
     */
    public static final String VERSION_STRING_REGEX = "[0-9]+\\.[0-9]+\\.[0-9]+";

    @Override
    public final int compare(@NotNull final String o1, @NotNull final String o2) {
        // Checks that both arguments are versions
        if (!o1.matches(VERSION_STRING_REGEX) || !o2.matches(VERSION_STRING_REGEX)) {
            throw new IllegalArgumentException("The compared arguments have to be versions");
        }

        // Splits the versions
        final String[] versionNumbers1 = o1.split("\\.");
        final String[] versionNumbers2 = o2.split("\\.");

        // Compares the major versions of both arguments
        final int majorVersionsComparison = versionNumbers1[0].compareTo(versionNumbers2[0]);

        // If the major versions are different, returns their comparison result
        if (0 != majorVersionsComparison) {
            return majorVersionsComparison;
        }

        // Compares the medium versions of both arguments
        final int mediumVersionsComparison = versionNumbers1[1].compareTo(versionNumbers2[1]);

        // If the medium versions are different, returns their comparison result
        if (0 != mediumVersionsComparison) {
            return mediumVersionsComparison;
        }

        // Returns the result of the comparison between the minor versions
        return versionNumbers1[2].compareTo(versionNumbers2[2]);
    }

}
