package fr.vulture.hostocars.comparator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link VersionComparator} class.
 */
@DisplayName("Version comparator")
class VersionComparatorTest {

    private static VersionComparator versionComparator;

    /**
     * Initializes the {@link VersionComparator}.
     */
    @BeforeAll
    static void init() {
        versionComparator = new VersionComparator();
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with the first argument invalid.
     */
    @Test
    @DisplayName("Comparison with invalid first argument")
    void testWithInvalidFirstArgument() {
        final IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> versionComparator.compare("Not a version", "1.0.0"),
                "Expected exception has not been thrown");
        assertEquals("The compared arguments have to be versions", exception.getMessage(), "Message different from expected");
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with the second argument invalid.
     */
    @Test
    @DisplayName("Comparison with invalid second argument")
    void testWithInvalidSecondArgument() {
        final IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> versionComparator.compare("1.0.0", "Not a version"),
                "Expected exception has not been thrown");
        assertEquals("The compared arguments have to be versions", exception.getMessage(), "Message different from expected");
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with arguments with different major versions.
     */
    @Test
    @DisplayName("Comparison with different major versions")
    void testWithDifferentMajorVersions() {
        assertAll("Asserting both ways comparisons",
            () -> assertTrue(versionComparator.compare("0.0.0", "1.0.0") < 0, "Result different from expected"),
            () -> assertTrue(versionComparator.compare("1.0.0", "0.0.0") > 0, "Result different from expected")
        );
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with arguments with different medium versions.
     */
    @Test
    @DisplayName("Comparison with different medium versions")
    void testWithDifferentMediumVersions() {
        assertAll("Asserting both ways comparisons",
            () -> assertTrue(versionComparator.compare("0.0.0", "0.1.0") < 0, "Result different from expected"),
            () -> assertTrue(versionComparator.compare("0.1.0", "0.0.0") > 0, "Result different from expected")
        );
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with arguments with different minor versions.
     */
    @Test
    @DisplayName("Comparison with different minor versions")
    void testWithDifferentMinorVersions() {
        assertAll("Asserting both ways comparisons",
            () -> assertTrue(versionComparator.compare("0.0.0", "0.0.1") < 0, "Result different from expected"),
            () -> assertTrue(versionComparator.compare("0.0.1", "0.0.0") > 0, "Result different from expected")
        );
    }

    /**
     * Tests the {@link VersionComparator#compare(String, String)} method with equal arguments.
     */
    @Test
    @DisplayName("Comparison with equal versions")
    void testWithEqualVersions() {
        assertEquals(0, versionComparator.compare("1.2.3", "1.2.3"), "Result different from expected");
    }

}
