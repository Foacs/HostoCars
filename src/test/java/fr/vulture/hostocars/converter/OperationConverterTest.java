package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.entity.OperationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationConverter} class.
 */
@DisplayName("Operation converter")
class OperationConverterTest {

    private final OperationConverter converter = new OperationConverter();

    /**
     * Tests the {@link OperationConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final Operation dto = new Operation();
        final Integer id = 0;
        dto.setId(id);
        final Integer interventionId = 1;
        dto.setInterventionId(interventionId);
        final String type = "type";
        dto.setType(type);

        final OperationEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(interventionId, entity.getInterventionId(), "Intervention ID different from expected");
        assertEquals(type, entity.getType(), "Type different from expected");
    }

    /**
     * Tests the {@link OperationConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link OperationConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final OperationEntity entity = new OperationEntity();
        final Integer id = 0;
        entity.setId(id);
        final Integer interventionId = 1;
        entity.setInterventionId(interventionId);
        final String type = "type";
        entity.setType(type);

        final Operation dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(interventionId, dto.getInterventionId(), "Intervention ID different from expected");
        assertEquals(type, dto.getType(), "Type different from expected");
    }

    /**
     * Tests the {@link OperationConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
