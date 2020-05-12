package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.Property;
import fr.vulture.hostocars.entity.PropertyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PropertyConverter} class.
 */
@DisplayName("Property converter")
class PropertyConverterTest {

    private final PropertyConverter converter = new PropertyConverter();

    /**
     * Tests the {@link PropertyConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final Property dto = new Property();
        final Integer id = 0;
        dto.setId(id);
        final String key = "key";
        dto.setKey(key);
        final String value = "value";
        dto.setValue(value);

        final PropertyEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(key, entity.getKey(), "Key different from expected");
        assertEquals(value, entity.getValue(), "Value different from expected");
    }

    /**
     * Tests the {@link PropertyConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link PropertyConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final PropertyEntity entity = new PropertyEntity();
        final Integer id = 0;
        entity.setId(id);
        final String key = "key";
        entity.setKey(key);
        final String value = "value";
        entity.setValue(value);

        final Property dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(key, dto.getKey(), "Key different from expected");
        assertEquals(value, dto.getValue(), "Value different from expected");
    }

    /**
     * Tests the {@link PropertyConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
