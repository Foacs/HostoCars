package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
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

import fr.vulture.hostocars.converter.OperationConverter;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationEntity;
import fr.vulture.hostocars.repository.OperationRepository;
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
 * Test class for the {@link OperationDao} class.
 */
@DisplayName("Operation DAO")
@ExtendWith(MockitoExtension.class)
class OperationDaoTest {

    @Mock
    private OperationLineDao operationLineDao;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationConverter operationConverter;

    @InjectMocks
    private OperationDao operationDao;

    /**
     * Tests the {@link OperationDao#getOperations} method.
     */
    @Test
    @DisplayName("Getting all operations")
    void testGetOperations() {
        final List<OperationEntity> entityList = emptyList();
        when(this.operationRepository.findAll()).thenReturn(entityList);

        final Operation dto = new Operation();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Operation> dtoList = singletonList(dto);
        when(this.operationConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<OperationLine> operationLineList = emptyList();
        when(this.operationLineDao.getOperationLinesByOperationId(dtoId)).thenReturn(operationLineList);

        assertEquals(dtoList, this.operationDao.getOperations(null), "Result list different from expected");
        assertEquals(operationLineList, dto.getOperationLineList(), "Result operation line list different from expected");

        verify(this.operationRepository, times(1)).findAll();
        verify(this.operationConverter, times(1)).toDtoList(entityList);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(dtoId);
    }

    /**
     * Tests the {@link OperationDao#getOperations} method with a sorting field.
     */
    @Test
    @DisplayName("Getting all operations with a sorting field")
    void testGetOperationsWithSortingField() {
        final List<OperationEntity> entityList = emptyList();
        when(this.operationRepository.findAll(any(Sort.class))).thenReturn(entityList);

        final Operation dto = new Operation();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Operation> dtoList = singletonList(dto);
        when(this.operationConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<OperationLine> operationLineList = emptyList();
        when(this.operationLineDao.getOperationLinesByOperationId(dtoId)).thenReturn(operationLineList);

        final String sortingField = "sortedBy";

        assertEquals(dtoList, this.operationDao.getOperations(sortingField), "Result list different from expected");
        assertEquals(operationLineList, dto.getOperationLineList(), "Result operation line list different from expected");

        final ArgumentCaptor<Sort> argumentCaptor = forClass(Sort.class);
        verify(this.operationRepository).findAll(argumentCaptor.capture());
        final Sort sort = argumentCaptor.getValue();

        assertNotNull(sort, "Sort clause unexpectedly null");
        assertEquals(1, sort.toList().size(), "Sort clause size different from expected");
        assertEquals(sortingField, sort.toList().get(0).getProperty(), "Sort clause property different from expected");

        verify(this.operationRepository, times(1)).findAll(sort);
        verify(this.operationConverter, times(1)).toDtoList(entityList);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(dtoId);
    }

    /**
     * Tests the {@link OperationDao#getOperationById} method.
     */
    @Test
    @DisplayName("Getting an operation by ID")
    void testGetOperationById() {
        final OperationEntity entity = new OperationEntity();
        final Integer id = 0;
        when(this.operationRepository.findById(id)).thenReturn(Optional.of(entity));

        final Operation dto = new Operation();
        dto.setId(id);
        when(this.operationConverter.toDto(entity)).thenReturn(dto);

        final List<OperationLine> operationLineList = emptyList();
        when(this.operationLineDao.getOperationLinesByOperationId(id)).thenReturn(operationLineList);

        assertEquals(dto, this.operationDao.getOperationById(id), "Result operation different from expected");
        assertEquals(operationLineList, dto.getOperationLineList(), "Result operation line list different from expected");

        verify(this.operationRepository, times(1)).findById(id);
        verify(this.operationConverter, times(1)).toDto(entity);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(id);
    }

    /**
     * Tests the {@link OperationDao#getOperationsByInterventionId} method.
     */
    @Test
    @DisplayName("Getting all operations by intervention ID")
    void testGetOperationsByInterventionId() {
        final Integer interventionId = 0;
        final List<OperationEntity> entityList = emptyList();
        when(this.operationRepository.findAllByInterventionId(interventionId)).thenReturn(entityList);

        final Operation dto = new Operation();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Operation> dtoList = singletonList(dto);
        when(this.operationConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<OperationLine> operationLineList = emptyList();
        when(this.operationLineDao.getOperationLinesByOperationId(dtoId)).thenReturn(operationLineList);

        assertEquals(dtoList, this.operationDao.getOperationsByInterventionId(interventionId), "Result list different from expected");
        assertEquals(operationLineList, dto.getOperationLineList(), "Result operation line list different from expected");

        verify(this.operationRepository, times(1)).findAllByInterventionId(interventionId);
        verify(this.operationConverter, times(1)).toDtoList(entityList);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(dtoId);
    }

    /**
     * Tests the {@link OperationDao#searchOperations} method.
     */
    @Test
    @DisplayName("Searching operations")
    void testSearchOperations() {
        final Operation body = new Operation();
        final OperationEntity entity = new OperationEntity();
        when(this.operationConverter.toEntity(body)).thenReturn(entity);

        final List<OperationEntity> entityList = emptyList();
        when(this.operationRepository.findAll(any(Example.class))).thenReturn(entityList);

        final Operation dto = new Operation();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Operation> dtoList = singletonList(dto);
        when(this.operationConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<OperationLine> operationLineList = emptyList();
        when(this.operationLineDao.getOperationLinesByOperationId(dtoId)).thenReturn(operationLineList);

        assertEquals(dtoList, this.operationDao.searchOperations(body), "Result list different from expected");
        assertEquals(operationLineList, dto.getOperationLineList(), "Result operation line list different from expected");

        final ArgumentCaptor<Example> argumentCaptor = forClass(Example.class);
        verify(this.operationRepository).findAll(argumentCaptor.capture());
        final Example example = argumentCaptor.getValue();

        assertNotNull(example, "Example unexpectedly null");
        assertEquals(DEFAULT_MATCHER, example.getMatcher(), "Example matcher different from expected");

        verify(this.operationRepository, times(1)).findAll(example);
        verify(this.operationConverter, times(1)).toDtoList(entityList);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(dtoId);
    }

    /**
     * Tests the {@link OperationDao#saveOperation} method.
     */
    @Test
    @DisplayName("Saving an operation")
    void testSaveOperation() {
        final Operation body = new Operation();

        final OperationLine operationLine = new OperationLine();
        body.setOperationLineList(singletonList(operationLine));
        when(this.operationLineDao.saveOperationLine(operationLine)).thenReturn(null);

        final OperationEntity entity = new OperationEntity();
        when(this.operationConverter.toEntity(body)).thenReturn(entity);

        final OperationEntity savedEntity = new OperationEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.operationRepository.save(entity)).thenReturn(savedEntity);

        assertEquals(id, this.operationDao.saveOperation(body), "Result ID different from expected");

        verify(this.operationConverter, times(1)).toEntity(body);
        verify(this.operationRepository, times(1)).save(entity);
        verify(this.operationLineDao, times(1)).saveOperationLine(operationLine);
    }

    /**
     * Tests the {@link OperationDao#updateOperation} method.
     */
    @Test
    @DisplayName("Updating an operation")
    void testUpdateOperation() {
        final Operation body = new Operation();

        final OperationLine operationLine = new OperationLine();
        body.setOperationLineList(singletonList(operationLine));
        doNothing().when(this.operationLineDao).updateOperationLine(operationLine);

        final OperationEntity entity = new OperationEntity();
        when(this.operationConverter.toEntity(body)).thenReturn(entity);

        final OperationEntity savedEntity = new OperationEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.operationRepository.save(entity)).thenReturn(null);

        this.operationDao.updateOperation(body);

        verify(this.operationConverter, times(1)).toEntity(body);
        verify(this.operationRepository, times(1)).save(entity);
        verify(this.operationLineDao, times(1)).updateOperationLine(operationLine);
    }

    /**
     * Tests the {@link OperationDao#deleteOperationById} method with an existing operation.
     */
    @Test
    @DisplayName("Deleting an existing operation")
    void testDeleteExistingOperationById() {
        final Integer id = 0;
        when(this.operationRepository.existsById(id)).thenReturn(true);

        final OperationLine operationLine = new OperationLine();
        final Integer operationLineId = 1;
        operationLine.setId(operationLineId);
        final List<OperationLine> operationLineList = singletonList(operationLine);
        when(this.operationLineDao.getOperationLinesByOperationId(id)).thenReturn(operationLineList);
        when(this.operationLineDao.deleteOperationLineById(operationLineId)).thenReturn(true);

        doNothing().when(this.operationRepository).deleteById(id);

        assertTrue(this.operationDao.deleteOperationById(id), "Result different from expected");

        verify(this.operationRepository, times(1)).existsById(id);
        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(id);
        verify(this.operationLineDao, times(1)).deleteOperationLineById(operationLineId);
        verify(this.operationRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the {@link OperationDao#deleteOperationById} method with an inexistant operation.
     */
    @Test
    @DisplayName("Deleting an inexistant operation")
    void testDeleteInexistantOperationById() {
        final Integer id = 0;
        when(this.operationRepository.existsById(id)).thenReturn(false);

        assertFalse(this.operationDao.deleteOperationById(id), "Result different from expected");

        verify(this.operationRepository, times(1)).existsById(id);
        verify(this.operationRepository, times(0)).deleteById(id);
    }

}
