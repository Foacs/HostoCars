package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for the {@link Converter} interface.
 */
@DisplayName("Converter")
@ExtendWith(MockitoExtension.class)
class ConverterTest {

    @Spy
    private final ConverterImpl converter = new ConverterImpl();

    /**
     * Tests the {@link Converter#toEntityList} method.
     */
    @Test
    @DisplayName("Converting to entity list")
    void testConvertToEntityList() {
        final Integer dto0 = 0;
        final String entity0 = "0";
        when(this.converter.toEntity(dto0)).thenReturn(entity0);

        final Integer dto1 = 1;
        final String entity1 = "1";
        when(this.converter.toEntity(dto1)).thenReturn(entity1);

        final List<String> entityList = this.converter.toEntityList(Arrays.asList(dto0, dto1));

        assertNotNull(entityList, "Entity list is unexpectedly null");
        assertEquals(2, entityList.size(), "Entity list size is different from expected");
        assertEquals(entity0, entityList.get(0), "First entity is different from expected");
        assertEquals(entity1, entityList.get(1), "Second entity is different from expected");

        verify(this.converter, times(1)).toEntity(dto0);
        verify(this.converter, times(1)).toEntity(dto1);
    }

    /**
     * Tests the {@link Converter#toEntityList} method with a null list.
     */
    @Test
    @DisplayName("Converting to entity list (null list)")
    void testConvertNullListToEntityList() {
        final List<String> entityList = this.converter.toEntityList(null);

        assertNotNull(entityList, "Entity list is unexpectedly null");
        assertTrue(entityList.isEmpty(), "Entity list is unexpectedly not empty");
    }

    /**
     * Tests the {@link Converter#toDtoList} method.
     */
    @Test
    @DisplayName("Converting to DTO list")
    void testConvertToDtoList() {
        final String entity0 = "0";
        final Integer dto0 = 0;
        when(this.converter.toDto(entity0)).thenReturn(dto0);

        final String entity1 = "1";
        final Integer dto1 = 1;
        when(this.converter.toDto(entity1)).thenReturn(dto1);

        final List<Integer> dtoList = this.converter.toDtoList(Arrays.asList(entity0, entity1));

        assertNotNull(dtoList, "DTO list is unexpectedly null");
        assertEquals(2, dtoList.size(), "DTO list size is different from expected");
        assertEquals(dto0, dtoList.get(0), "First DTO is different from expected");
        assertEquals(dto1, dtoList.get(1), "Second DTO is different from expected");

        verify(this.converter, times(1)).toDto(entity0);
        verify(this.converter, times(1)).toDto(entity1);
    }

    /**
     * Tests the {@link Converter#toDtoList} method with a null list.
     */
    @Test
    @DisplayName("Converting to DTO list (null list)")
    void testConvertNullListToDtoList() {
        final List<Integer> dtoList = this.converter.toDtoList(null);

        assertNotNull(dtoList, "DTO list is unexpectedly null");
        assertTrue(dtoList.isEmpty(), "DTO list is unexpectedly not empty");
    }

}

