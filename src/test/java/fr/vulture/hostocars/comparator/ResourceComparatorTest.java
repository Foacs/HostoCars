package fr.vulture.hostocars.comparator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

/**
 * Test class for the {@link ResourceComparator} class.
 */
@DisplayName("Resource comparator")
class ResourceComparatorTest {

    private static ResourceComparator resourceComparator;

    /**
     * Initializes the {@link ResourceComparator}.
     */
    @BeforeAll
    static void init() {
        resourceComparator = new ResourceComparator();
    }

    /**
     * Tests the {@link ResourceComparator#compare(Resource, Resource)} method with an invalid first argument.
     */
    @Test
    @DisplayName("Comparison with invalid first argument")
    final void testWithInvalidFirstArgument() {
        final Resource firstResource = Mockito.mock(Resource.class);
        final Resource secondResource = Mockito.mock(Resource.class);

        Mockito.when(firstResource.getFilename()).thenReturn(null);

        final IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> resourceComparator.compare(firstResource, secondResource),
                "Expected exception has not been thrown");
        assertEquals("The compared resources have to have a file name", exception.getMessage(), "Message different from expected");

        Mockito.verify(firstResource, Mockito.times(1)).getFilename();
    }

    /**
     * Tests the {@link ResourceComparator#compare(Resource, Resource)} method with an invalid second argument.
     */
    @Test
    @DisplayName("Comparison with invalid second argument")
    final void testWithInvalidSecondArgument() {
        final Resource firstResource = Mockito.mock(Resource.class);
        final Resource secondResource = Mockito.mock(Resource.class);

        Mockito.when(firstResource.getFilename()).thenReturn("");
        Mockito.when(secondResource.getFilename()).thenReturn(null);

        final IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> resourceComparator.compare(firstResource, secondResource),
                "Expected exception has not been thrown");
        assertEquals("The compared resources have to have a file name", exception.getMessage(), "Message different from expected");

        Mockito.verify(firstResource, Mockito.times(1)).getFilename();
        Mockito.verify(secondResource, Mockito.times(1)).getFilename();
    }

    /**
     * Tests the {@link ResourceComparator#compare(Resource, Resource)} method with resources with different filenames.
     */
    @Test
    @DisplayName("Comparison with different filenames")
    final void testWithDifferentFilenames() {
        final Resource firstResource = Mockito.mock(Resource.class);
        final Resource secondResource = Mockito.mock(Resource.class);

        Mockito.when(firstResource.getFilename()).thenReturn("A");
        Mockito.when(secondResource.getFilename()).thenReturn("B");

        assertAll("Asserting both ways comparisons",
            () -> assertTrue(0 > resourceComparator.compare(firstResource, secondResource), "Result different from expected"),
            () -> assertTrue(0 < resourceComparator.compare(secondResource, firstResource), "Result different from expected")
        );

        Mockito.verify(firstResource, Mockito.times(2)).getFilename();
        Mockito.verify(secondResource, Mockito.times(2)).getFilename();
    }

    /**
     * Tests the {@link ResourceComparator#compare(Resource, Resource)} method with resources with equal filenames.
     */
    @Test
    @DisplayName("Comparison with equal filenames")
    final void testWithEqualFilenames() {
        final Resource firstResource = Mockito.mock(Resource.class);
        final Resource secondResource = Mockito.mock(Resource.class);

        Mockito.when(firstResource.getFilename()).thenReturn("A");
        Mockito.when(secondResource.getFilename()).thenReturn("A");

        assertEquals(0, resourceComparator.compare(firstResource, secondResource), "Result different from expected");

        Mockito.verify(firstResource, Mockito.times(1)).getFilename();
        Mockito.verify(secondResource, Mockito.times(1)).getFilename();
    }

}
