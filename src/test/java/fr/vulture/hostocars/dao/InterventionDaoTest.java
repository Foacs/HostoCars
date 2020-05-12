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

import fr.vulture.hostocars.converter.InterventionConverter;
import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.entity.InterventionEntity;
import fr.vulture.hostocars.repository.InterventionRepository;
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
 * Test class for the {@link InterventionDao} class.
 */
@DisplayName("Intervention DAO")
@ExtendWith(MockitoExtension.class)
class InterventionDaoTest {

    @Mock
    private OperationDao operationDao;

    @Mock
    private ConsumableDao consumableDao;

    @Mock
    private InterventionRepository interventionRepository;

    @Mock
    private InterventionConverter interventionConverter;

    @InjectMocks
    private InterventionDao interventionDao;

    /**
     * Tests the {@link InterventionDao#getInterventions} method.
     */
    @Test
    @DisplayName("Getting all interventions")
    void testGetInterventions() {
        final List<InterventionEntity> entityList = emptyList();
        when(this.interventionRepository.findAll()).thenReturn(entityList);

        final Intervention dto = new Intervention();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Intervention> dtoList = singletonList(dto);
        when(this.interventionConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<Operation> operationList = emptyList();
        when(this.operationDao.getOperationsByInterventionId(dtoId)).thenReturn(operationList);

        final List<Consumable> consumableList = emptyList();
        when(this.consumableDao.getConsumablesByInterventionId(dtoId)).thenReturn(consumableList);

        assertEquals(dtoList, this.interventionDao.getInterventions(null), "Result list different from expected");
        assertEquals(operationList, dto.getOperationList(), "Result operation list different from expected");
        assertEquals(consumableList, dto.getConsumableList(), "Result consumable list different from expected");

        verify(this.interventionRepository, times(1)).findAll();
        verify(this.interventionConverter, times(1)).toDtoList(entityList);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(dtoId);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(dtoId);
    }

    /**
     * Tests the {@link InterventionDao#getInterventions} method with a sorting field.
     */
    @Test
    @DisplayName("Getting all interventions with a sorting field")
    void testGetInterventionsWithSortingField() {
        final List<InterventionEntity> entityList = emptyList();
        when(this.interventionRepository.findAll(any(Sort.class))).thenReturn(entityList);

        final Intervention dto = new Intervention();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Intervention> dtoList = singletonList(dto);
        when(this.interventionConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<Operation> operationList = emptyList();
        when(this.operationDao.getOperationsByInterventionId(dtoId)).thenReturn(operationList);

        final List<Consumable> consumableList = emptyList();
        when(this.consumableDao.getConsumablesByInterventionId(dtoId)).thenReturn(consumableList);

        final String sortingField = "sortedBy";

        assertEquals(dtoList, this.interventionDao.getInterventions(sortingField), "Result list different from expected");
        assertEquals(operationList, dto.getOperationList(), "Result operation list different from expected");
        assertEquals(consumableList, dto.getConsumableList(), "Result consumable list different from expected");

        final ArgumentCaptor<Sort> argumentCaptor = forClass(Sort.class);
        verify(this.interventionRepository).findAll(argumentCaptor.capture());
        final Sort sort = argumentCaptor.getValue();

        assertNotNull(sort, "Sort clause unexpectedly null");
        assertEquals(1, sort.toList().size(), "Sort clause size different from expected");
        assertEquals(sortingField, sort.toList().get(0).getProperty(), "Sort clause property different from expected");

        verify(this.interventionRepository, times(1)).findAll(sort);
        verify(this.interventionConverter, times(1)).toDtoList(entityList);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(dtoId);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(dtoId);
    }

    /**
     * Tests the {@link InterventionDao#getInterventionById} method.
     */
    @Test
    @DisplayName("Getting an intervention by ID")
    void testGetInterventionById() {
        final InterventionEntity entity = new InterventionEntity();
        final Integer id = 0;
        when(this.interventionRepository.findById(id)).thenReturn(Optional.of(entity));

        final Intervention dto = new Intervention();
        dto.setId(id);
        when(this.interventionConverter.toDto(entity)).thenReturn(dto);

        final List<Operation> operationList = emptyList();
        when(this.operationDao.getOperationsByInterventionId(id)).thenReturn(operationList);

        final List<Consumable> consumableList = emptyList();
        when(this.consumableDao.getConsumablesByInterventionId(id)).thenReturn(consumableList);

        assertEquals(dto, this.interventionDao.getInterventionById(id), "Result intervention different from expected");
        assertEquals(operationList, dto.getOperationList(), "Result operation list different from expected");
        assertEquals(consumableList, dto.getConsumableList(), "Result consumable list different from expected");

        verify(this.interventionRepository, times(1)).findById(id);
        verify(this.interventionConverter, times(1)).toDto(entity);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(id);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(id);
    }

    /**
     * Tests the {@link InterventionDao#getInterventionsByCarId} method.
     */
    @Test
    @DisplayName("Getting all interventions by car ID")
    void testGetInterventionsByCarId() {
        final Integer carId = 0;
        final List<InterventionEntity> entityList = emptyList();
        when(this.interventionRepository.findAllByCarId(carId)).thenReturn(entityList);

        final Intervention dto = new Intervention();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Intervention> dtoList = singletonList(dto);
        when(this.interventionConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<Operation> operationList = emptyList();
        when(this.operationDao.getOperationsByInterventionId(dtoId)).thenReturn(operationList);

        final List<Consumable> consumableList = emptyList();
        when(this.consumableDao.getConsumablesByInterventionId(dtoId)).thenReturn(consumableList);

        assertEquals(dtoList, this.interventionDao.getInterventionsByCarId(carId), "Result list different from expected");
        assertEquals(operationList, dto.getOperationList(), "Result operation list different from expected");
        assertEquals(consumableList, dto.getConsumableList(), "Result consumable list different from expected");

        verify(this.interventionRepository, times(1)).findAllByCarId(carId);
        verify(this.interventionConverter, times(1)).toDtoList(entityList);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(dtoId);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(dtoId);
    }

    /**
     * Tests the {@link InterventionDao#searchInterventions} method.
     */
    @Test
    @DisplayName("Searching interventions")
    void testSearchInterventions() {
        final Intervention body = new Intervention();
        final InterventionEntity entity = new InterventionEntity();
        when(this.interventionConverter.toEntity(body)).thenReturn(entity);

        final List<InterventionEntity> entityList = emptyList();
        when(this.interventionRepository.findAll(any(Example.class))).thenReturn(entityList);

        final Intervention dto = new Intervention();
        final Integer dtoId = 0;
        dto.setId(dtoId);
        final List<Intervention> dtoList = singletonList(dto);
        when(this.interventionConverter.toDtoList(entityList)).thenReturn(dtoList);

        final List<Operation> operationList = emptyList();
        when(this.operationDao.getOperationsByInterventionId(dtoId)).thenReturn(operationList);

        final List<Consumable> consumableList = emptyList();
        when(this.consumableDao.getConsumablesByInterventionId(dtoId)).thenReturn(consumableList);

        assertEquals(dtoList, this.interventionDao.searchInterventions(body), "Result list different from expected");
        assertEquals(operationList, dto.getOperationList(), "Result operation list different from expected");
        assertEquals(consumableList, dto.getConsumableList(), "Result consumable list different from expected");

        final ArgumentCaptor<Example> argumentCaptor = forClass(Example.class);
        verify(this.interventionRepository).findAll(argumentCaptor.capture());
        final Example example = argumentCaptor.getValue();

        assertNotNull(example, "Example unexpectedly null");
        assertEquals(DEFAULT_MATCHER.withIgnorePaths("certificate", "picture"), example.getMatcher(), "Example matcher different from expected");

        verify(this.interventionRepository, times(1)).findAll(example);
        verify(this.interventionConverter, times(1)).toDtoList(entityList);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(dtoId);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(dtoId);
    }

    /**
     * Tests the {@link InterventionDao#saveIntervention} method.
     */
    @Test
    @DisplayName("Saving an intervention")
    void testSaveIntervention() {
        final Intervention body = new Intervention();

        final Operation operation = new Operation();
        body.setOperationList(singletonList(operation));
        when(this.operationDao.saveOperation(operation)).thenReturn(null);

        final Consumable consumable = new Consumable();
        body.setConsumableList(singletonList(consumable));
        when(this.consumableDao.saveConsumable(consumable)).thenReturn(null);

        final InterventionEntity entity = new InterventionEntity();
        when(this.interventionConverter.toEntity(body)).thenReturn(entity);

        final InterventionEntity savedEntity = new InterventionEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.interventionRepository.save(entity)).thenReturn(savedEntity);

        assertEquals(id, this.interventionDao.saveIntervention(body), "Result ID different from expected");

        verify(this.interventionConverter, times(1)).toEntity(body);
        verify(this.interventionRepository, times(1)).save(entity);
        verify(this.operationDao, times(1)).saveOperation(operation);
        verify(this.consumableDao, times(1)).saveConsumable(consumable);
    }

    /**
     * Tests the {@link InterventionDao#updateIntervention} method.
     */
    @Test
    @DisplayName("Updating an intervention")
    void testUpdateIntervention() {
        final Intervention body = new Intervention();

        final Operation operation = new Operation();
        body.setOperationList(singletonList(operation));
        doNothing().when(this.operationDao).updateOperation(operation);

        final Consumable consumable = new Consumable();
        body.setConsumableList(singletonList(consumable));
        doNothing().when(this.consumableDao).updateConsumable(consumable);

        final InterventionEntity entity = new InterventionEntity();
        when(this.interventionConverter.toEntity(body)).thenReturn(entity);

        final InterventionEntity savedEntity = new InterventionEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.interventionRepository.save(entity)).thenReturn(null);

        this.interventionDao.updateIntervention(body);

        verify(this.interventionConverter, times(1)).toEntity(body);
        verify(this.interventionRepository, times(1)).save(entity);
        verify(this.operationDao, times(1)).updateOperation(operation);
        verify(this.consumableDao, times(1)).updateConsumable(consumable);
    }

    /**
     * Tests the {@link InterventionDao#deleteInterventionById} method with an existing intervention.
     */
    @Test
    @DisplayName("Deleting an existing intervention")
    void testDeleteExistingInterventionById() {
        final Integer id = 0;
        when(this.interventionRepository.existsById(id)).thenReturn(true);

        final Operation operation = new Operation();
        final Integer operationId = 1;
        operation.setId(operationId);
        final List<Operation> operationList = singletonList(operation);
        when(this.operationDao.getOperationsByInterventionId(id)).thenReturn(operationList);
        when(this.operationDao.deleteOperationById(operationId)).thenReturn(true);

        final Consumable consumable = new Consumable();
        final Integer consumableId = 1;
        consumable.setId(consumableId);
        final List<Consumable> consumableList = singletonList(consumable);
        when(this.consumableDao.getConsumablesByInterventionId(id)).thenReturn(consumableList);
        when(this.consumableDao.deleteConsumableById(consumableId)).thenReturn(true);

        doNothing().when(this.interventionRepository).deleteById(id);

        assertTrue(this.interventionDao.deleteInterventionById(id), "Result different from expected");

        verify(this.interventionRepository, times(1)).existsById(id);
        verify(this.operationDao, times(1)).getOperationsByInterventionId(id);
        verify(this.operationDao, times(1)).deleteOperationById(operationId);
        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(id);
        verify(this.consumableDao, times(1)).deleteConsumableById(consumableId);
        verify(this.interventionRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the {@link InterventionDao#deleteInterventionById} method with an inexistant intervention.
     */
    @Test
    @DisplayName("Deleting an inexistant intervention")
    void testDeleteInexistantInterventionById() {
        final Integer id = 0;
        when(this.interventionRepository.existsById(id)).thenReturn(false);

        assertFalse(this.interventionDao.deleteInterventionById(id), "Result different from expected");

        verify(this.interventionRepository, times(1)).existsById(id);
        verify(this.interventionRepository, times(0)).deleteById(id);
    }

}
