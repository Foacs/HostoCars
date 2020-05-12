package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationLineEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLineConverter} class.
 */
@DisplayName("Operation line converter")
class OperationLineConverterTest {

    private final OperationLineConverter converter = new OperationLineConverter();

    /**
     * Tests the {@link OperationLineConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final OperationLine dto = new OperationLine();
        final Integer id = 0;
        dto.setId(id);
        final Integer operationId = 1;
        dto.setOperationId(operationId);
        final String description = "description";
        dto.setDescription(description);
        final Boolean done = Boolean.TRUE;
        dto.setDone(done);

        final OperationLineEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(operationId, entity.getOperationId(), "Operation ID different from expected");
        assertEquals(description, entity.getDescription(), "Description different from expected");
        assertEquals(done, entity.getDone(), "Done flag different from expected");
    }

    /**
     * Tests the {@link OperationLineConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link OperationLineConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final OperationLineEntity entity = new OperationLineEntity();
        final Integer id = 0;
        entity.setId(id);
        final Integer operationId = 1;
        entity.setOperationId(operationId);
        final String description = "description";
        entity.setDescription(description);
        final Boolean done = Boolean.TRUE;
        entity.setDone(done);

        final OperationLine dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(operationId, dto.getOperationId(), "Operation ID different from expected");
        assertEquals(description, dto.getDescription(), "Description different from expected");
        assertEquals(done, dto.getDone(), "Done flag different from expected");
    }

    /**
     * Tests the {@link OperationLineConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
