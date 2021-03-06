package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.dto.OperationLine;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for the {@link OperationConverter} class.
 */
@DisplayName("Operation converter")
@ExtendWith(MockitoExtension.class)
class OperationConverterTest {

    @Mock
    private OperationLineConverter operationLineConverter;

    @InjectMocks
    private OperationConverter operationConverter;

    /**
     * Tests the {@link OperationConverter#toEntity(Operation)} method with a null DTO.
     */
    @Test
    @DisplayName("To entity (null DTO)")
    void testToEntityWithNullDto() {
        // Calls the method
        final fr.vulture.hostocars.entity.Operation result = this.operationConverter.toEntity(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link OperationConverter#toEntity(Operation)} method.
     */
    @Test
    @DisplayName("To entity")
    void testToEntity() {
        // Prepares the inputs
        final Integer id = 0;
        final String label = "label";
        final Set<OperationLine> operationLines = mock(Set.class);
        final Operation dto = new Operation();
        dto.setId(id);
        dto.setLabel(label);
        dto.setOperationLines(operationLines);

        // Prepares the intermediary results
        final Set<fr.vulture.hostocars.entity.OperationLine> convertedOperationLines = mock(Set.class);

        // Mocks the calls
        doReturn(convertedOperationLines).when(this.operationLineConverter).toEntities(operationLines);

        // Calls the method
        final fr.vulture.hostocars.entity.Operation result = this.operationConverter.toEntity(dto);

        // Checks the mocks calls
        verify(this.operationLineConverter).toEntities(operationLines);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(label, result.getLabel(), "Label different from expected"),
            () -> assertEquals(convertedOperationLines, result.getOperationLines(), "Operation lines different from expected"));
    }

    /**
     * Tests the {@link OperationConverter#toDto(fr.vulture.hostocars.entity.Operation)} method with a null entity.
     */
    @Test
    @DisplayName("To DTO (null entity)")
    void testToDtoWithNullEntity() {
        // Calls the method
        final Operation result = this.operationConverter.toDto(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link OperationConverter#toDto(fr.vulture.hostocars.entity.Operation)} method.
     */
    @Test
    @DisplayName("To DTO")
    void testToDto() {
        // Prepares the inputs
        final Integer id = 0;
        final String label = "label";
        final Set<fr.vulture.hostocars.entity.OperationLine> operationLines = mock(Set.class);
        final fr.vulture.hostocars.entity.Operation dto = new fr.vulture.hostocars.entity.Operation();
        dto.setId(id);
        dto.setLabel(label);
        dto.setOperationLines(operationLines);

        // Prepares the intermediary results
        final Set<OperationLine> convertedOperationLines = mock(Set.class);

        // Mocks the calls
        doReturn(convertedOperationLines).when(this.operationLineConverter).toDtos(operationLines);

        // Calls the method
        final Operation result = this.operationConverter.toDto(dto);

        // Checks the mocks calls
        verify(this.operationLineConverter).toDtos(operationLines);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(label, result.getLabel(), "Label different from expected"),
            () -> assertEquals(convertedOperationLines, result.getOperationLines(), "Operation lines different from expected"));
    }

}
