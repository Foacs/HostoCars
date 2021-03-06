package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.OperationLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLineConverter} class.
 */
@DisplayName("Operation line converter")
class OperationLineConverterTest {

    private final OperationLineConverter operationLineConverter = new OperationLineConverter();

    /**
     * Tests the {@link OperationLineConverter#toEntity(OperationLine)} method with a null DTO.
     */
    @Test
    @DisplayName("To entity (null DTO)")
    void testToEntityWithNullDto() {
        // Calls the method
        final fr.vulture.hostocars.entity.OperationLine result = this.operationLineConverter.toEntity(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link OperationLineConverter#toEntity(OperationLine)} method.
     */
    @Test
    @DisplayName("To entity")
    void testToEntity() {
        // Prepares the inputs
        final Integer id = 0;
        final String description = "description";
        final String type = "type";
        final Boolean done = true;
        final OperationLine dto = new OperationLine();
        dto.setId(id);
        dto.setDescription(description);
        dto.setType(type);
        dto.setDone(done);

        // Calls the method
        final fr.vulture.hostocars.entity.OperationLine result = this.operationLineConverter.toEntity(dto);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(description, result.getDescription(), "Description different from expected"),
            () -> assertEquals(type, result.getType(), "Type different from expected"), () -> assertEquals(done, result.getDone(), "Done flag different from expected"));
    }

    /**
     * Tests the {@link OperationLineConverter#toDto(fr.vulture.hostocars.entity.OperationLine)} method with a null entity.
     */
    @Test
    @DisplayName("To DTO (null entity)")
    void testToDtoWithNullEntity() {
        // Calls the method
        final OperationLine result = this.operationLineConverter.toDto(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link OperationLineConverter#toDto(fr.vulture.hostocars.entity.OperationLine)} method.
     */
    @Test
    @DisplayName("To DTO")
    void testToDto() {
        // Prepares the inputs
        final Integer id = 0;
        final String description = "description";
        final String type = "type";
        final Boolean done = true;
        final fr.vulture.hostocars.entity.OperationLine dto = new fr.vulture.hostocars.entity.OperationLine();
        dto.setId(id);
        dto.setDescription(description);
        dto.setType(type);
        dto.setDone(done);

        // Calls the method
        final OperationLine result = this.operationLineConverter.toDto(dto);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(description, result.getDescription(), "Description different from expected"),
            () -> assertEquals(type, result.getType(), "Type different from expected"), () -> assertEquals(done, result.getDone(), "Done flag different from expected"));
    }

}
