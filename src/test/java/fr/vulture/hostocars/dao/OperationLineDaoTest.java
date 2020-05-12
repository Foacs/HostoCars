package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.vulture.hostocars.converter.OperationLineConverter;
import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationLineEntity;
import fr.vulture.hostocars.repository.OperationLineRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

/**
 * Test class for the {@link OperationLineDao} class.
 */
@DisplayName("OperationLine DAO")
@ExtendWith(MockitoExtension.class)
class OperationLineDaoTest {

    @Mock
    private OperationLineRepository operationLineRepository;

    @Mock
    private OperationLineConverter operationLineConverter;

    @InjectMocks
    private OperationLineDao operationLineDao;

    /**
     * Tests the {@link OperationLineDao#getOperationLines} method.
     */
    @Test
    @DisplayName("Getting all operation lines")
    void testGetOperationLines() {
        final List<OperationLineEntity> entityList = emptyList();
        when(this.operationLineRepository.findAll()).thenReturn(entityList);

        final List<OperationLine> dtoList = emptyList();
        when(this.operationLineConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.operationLineDao.getOperationLines(null), "Result list different from expected");

        verify(this.operationLineRepository, times(1)).findAll();
        verify(this.operationLineConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link OperationLineDao#getOperationLines} method with a sorting field.
     */
    @Test
    @DisplayName("Getting all operation lines with a sorting field")
    void testGetOperationLinesWithSortingField() {
        final List<OperationLineEntity> entityList = emptyList();
        when(this.operationLineRepository.findAll(any(Sort.class))).thenReturn(entityList);

        final List<OperationLine> dtoList = emptyList();
        when(this.operationLineConverter.toDtoList(entityList)).thenReturn(dtoList);

        final String sortingField = "sortedBy";

        assertEquals(dtoList, this.operationLineDao.getOperationLines(sortingField), "Result list different from expected");

        final ArgumentCaptor<Sort> argumentCaptor = forClass(Sort.class);
        verify(this.operationLineRepository).findAll(argumentCaptor.capture());
        final Sort sort = argumentCaptor.getValue();

        assertNotNull(sort, "Sort clause unexpectedly null");
        assertEquals(1, sort.toList().size(), "Sort clause size different from expected");
        assertEquals(sortingField, sort.toList().get(0).getProperty(), "Sort clause property different from expected");

        verify(this.operationLineRepository, times(1)).findAll(sort);
        verify(this.operationLineConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link OperationLineDao#getOperationLineById} method.
     */
    @Test
    @DisplayName("Getting an operation line by ID")
    void testGetOperationLineById() {
        final OperationLineEntity entity = new OperationLineEntity();
        final Integer id = 0;
        when(this.operationLineRepository.findById(id)).thenReturn(Optional.of(entity));

        final OperationLine dto = new OperationLine();
        when(this.operationLineConverter.toDto(entity)).thenReturn(dto);

        assertEquals(dto, this.operationLineDao.getOperationLineById(id), "Result operation line different from expected");

        verify(this.operationLineRepository, times(1)).findById(id);
        verify(this.operationLineConverter, times(1)).toDto(entity);
    }

    /**
     * Tests the {@link OperationLineDao#getOperationLinesByOperationId} method.
     */
    @Test
    @DisplayName("Getting all operation lines by operation ID")
    void testGetOperationLinesByOperationId() {
        final Integer operationId = 0;
        final List<OperationLineEntity> entityList = emptyList();
        when(this.operationLineRepository.findAllByOperationId(operationId)).thenReturn(entityList);

        final List<OperationLine> dtoList = emptyList();
        when(this.operationLineConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.operationLineDao.getOperationLinesByOperationId(operationId), "Result list different from expected");

        verify(this.operationLineRepository, times(1)).findAllByOperationId(operationId);
        verify(this.operationLineConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link OperationLineDao#searchOperationLines} method.
     */
    @Test
    @DisplayName("Searching operation lines")
    void testSearchOperationLines() {
        final OperationLine body = new OperationLine();
        final OperationLineEntity entity = new OperationLineEntity();
        when(this.operationLineConverter.toEntity(body)).thenReturn(entity);

        final List<OperationLineEntity> entityList = emptyList();
        when(this.operationLineRepository.findAll(any(Example.class))).thenReturn(entityList);

        final List<OperationLine> dtoList = emptyList();
        when(this.operationLineConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.operationLineDao.searchOperationLines(body), "Result list different from expected");

        final ArgumentCaptor<Example> argumentCaptor = forClass(Example.class);
        verify(this.operationLineRepository).findAll(argumentCaptor.capture());
        final Example example = argumentCaptor.getValue();

        assertNotNull(example, "Example unexpectedly null");
        assertEquals(DEFAULT_MATCHER, example.getMatcher(), "Example matcher different from expected");

        verify(this.operationLineRepository, times(1)).findAll(example);
        verify(this.operationLineConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link OperationLineDao#saveOperationLine} method.
     */
    @Test
    @DisplayName("Saving an operation line")
    void testSaveOperationLine() {
        final OperationLine body = new OperationLine();
        final OperationLineEntity entity = new OperationLineEntity();
        when(this.operationLineConverter.toEntity(body)).thenReturn(entity);

        final OperationLineEntity savedEntity = new OperationLineEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.operationLineRepository.save(entity)).thenReturn(savedEntity);

        assertEquals(id, this.operationLineDao.saveOperationLine(body), "Result ID different from expected");

        verify(this.operationLineConverter, times(1)).toEntity(body);
        verify(this.operationLineRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link OperationLineDao#updateOperationLine} method.
     */
    @Test
    @DisplayName("Updating an operation line")
    void testUpdateOperationLine() {
        final OperationLine body = new OperationLine();
        final OperationLineEntity entity = new OperationLineEntity();
        when(this.operationLineConverter.toEntity(body)).thenReturn(entity);

        final OperationLineEntity savedEntity = new OperationLineEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.operationLineRepository.save(entity)).thenReturn(null);

        this.operationLineDao.updateOperationLine(body);

        verify(this.operationLineConverter, times(1)).toEntity(body);
        verify(this.operationLineRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link OperationLineDao#deleteOperationLineById} method with an existing operation line.
     */
    @Test
    @DisplayName("Deleting an existing operation line")
    void testDeleteExistingOperationLineById() {
        final Integer id = 0;
        when(this.operationLineRepository.existsById(id)).thenReturn(true);
        doNothing().when(this.operationLineRepository).deleteById(id);

        assertTrue(this.operationLineDao.deleteOperationLineById(id), "Result different from expected");

        verify(this.operationLineRepository, times(1)).existsById(id);
        verify(this.operationLineRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the {@link OperationLineDao#deleteOperationLineById} method with an inexistant operation line.
     */
    @Test
    @DisplayName("Deleting an inexistant operation line")
    void testDeleteInexistantOperationLineById() {
        final Integer id = 0;
        when(this.operationLineRepository.existsById(id)).thenReturn(false);

        assertFalse(this.operationLineDao.deleteOperationLineById(id), "Result different from expected");

        verify(this.operationLineRepository, times(1)).existsById(id);
        verify(this.operationLineRepository, times(0)).deleteById(id);
    }

}
