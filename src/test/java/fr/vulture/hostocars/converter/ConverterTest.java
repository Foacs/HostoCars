package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Converter} interface.
 */
class ConverterTest {

    private final ConverterImpl converter = mock(ConverterImpl.class);

    /**
     * Tests the {@link Converter#toEntities(Collection)} method with a null input.
     */
    @Test
    @DisplayName("To entities (null input)")
    void testToEntitiesWithNullInput() {
        // Mocks the calls
        doCallRealMethod().when(this.converter).toEntities(null);

        // Calls the method
        final Set<Object> result = this.converter.toEntities(null);

        // Checks the mocks calls
        verify(this.converter).toEntities(null);

        // Checks the result
        assertTrue(result.isEmpty(), "Result unexpectedly not empty");
    }

    /**
     * Tests the {@link Converter#toEntities(Collection)} method.
     */
    @Test
    @DisplayName("To entities")
    void testToEntities() {
        // Prepares the inputs
        final List<Object> dtos = Arrays.asList(mock(Object.class), mock(Object.class));

        // Prepares the intermediary results
        final List<Object> entities = Arrays.asList(mock(Object.class), mock(Object.class));

        // Mocks the calls
        doCallRealMethod().when(this.converter).toEntities(dtos);
        doReturn(entities.get(0)).when(this.converter).toEntity(dtos.get(0));
        doReturn(entities.get(1)).when(this.converter).toEntity(dtos.get(1));

        // Calls the method
        final Set<Object> result = this.converter.toEntities(dtos);

        // Checks the mocks calls
        verify(this.converter).toEntities(dtos);
        verify(this.converter).toEntity(dtos.get(0));
        verify(this.converter).toEntity(dtos.get(1));

        // Checks the result
        assertEquals(2, result.size(), "Unexpected number of results");
        assertTrue(result.containsAll(entities), "Unexpected results");
    }

    /**
     * Tests the {@link Converter#toDtos(Collection)} method with a null input.
     */
    @Test
    @DisplayName("To DTOs (null input)")
    void testToDtosWithNullInput() {
        // Mocks the calls
        doCallRealMethod().when(this.converter).toDtos(null);

        // Calls the method
        final Set<Object> result = this.converter.toDtos(null);

        // Checks the mocks calls
        verify(this.converter).toDtos(null);

        // Checks the result
        assertTrue(result.isEmpty(), "Result unexpectedly not empty");
    }

    /**
     * Tests the {@link Converter#toDtos(Collection)} method.
     */
    @Test
    @DisplayName("To DTOs")
    void testToDtos() {
        // Prepares the inputs
        final List<Object> entities = Arrays.asList(mock(Object.class), mock(Object.class));

        // Prepares the intermediary results
        final List<Object> dtos = Arrays.asList(mock(Object.class), mock(Object.class));

        // Mocks the calls
        doCallRealMethod().when(this.converter).toDtos(entities);
        doReturn(dtos.get(0)).when(this.converter).toDto(entities.get(0));
        doReturn(dtos.get(1)).when(this.converter).toDto(entities.get(1));

        // Calls the method
        final Set<Object> result = this.converter.toDtos(entities);

        // Checks the mocks calls
        verify(this.converter).toDtos(entities);
        verify(this.converter).toDto(entities.get(0));
        verify(this.converter).toDto(entities.get(1));

        // Checks the result
        assertEquals(2, result.size(), "Unexpected number of results");
        assertTrue(result.containsAll(dtos), "Unexpected results");
    }

    /**
     * Implementation of the {@link Converter} interface for unit tests.
     */
    private static class ConverterImpl implements Converter<Object, Object> {

        /**
         * {@inheritDoc}
         */
        @Override
        public Object toEntity(final Object dto) {
            throw new AssertionError("Unexpected call to test implementation");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object toDto(final Object entity) {
            throw new AssertionError("Unexpected call to test implementation");
        }

    }

}
