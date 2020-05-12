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

import fr.vulture.hostocars.converter.ConsumableConverter;
import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.entity.ConsumableEntity;
import fr.vulture.hostocars.repository.ConsumableRepository;
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
 * Test class for the {@link ConsumableDao} class.
 */
@DisplayName("Consumable DAO")
@ExtendWith(MockitoExtension.class)
class ConsumableDaoTest {

    @Mock
    private ConsumableRepository consumableRepository;

    @Mock
    private ConsumableConverter consumableConverter;

    @InjectMocks
    private ConsumableDao consumableDao;

    /**
     * Tests the {@link ConsumableDao#getConsumables} method.
     */
    @Test
    @DisplayName("Getting all consumables")
    void testGetConsumables() {
        final List<ConsumableEntity> entityList = emptyList();
        when(this.consumableRepository.findAll()).thenReturn(entityList);

        final List<Consumable> dtoList = emptyList();
        when(this.consumableConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.consumableDao.getConsumables(null), "Result list different from expected");

        verify(this.consumableRepository, times(1)).findAll();
        verify(this.consumableConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link ConsumableDao#getConsumables} method with a sorting field.
     */
    @Test
    @DisplayName("Getting all consumables with a sorting field")
    void testGetConsumablesWithSortingField() {
        final List<ConsumableEntity> entityList = emptyList();
        when(this.consumableRepository.findAll(any(Sort.class))).thenReturn(entityList);

        final List<Consumable> dtoList = emptyList();
        when(this.consumableConverter.toDtoList(entityList)).thenReturn(dtoList);

        final String sortingField = "sortedBy";

        assertEquals(dtoList, this.consumableDao.getConsumables(sortingField), "Result list different from expected");

        final ArgumentCaptor<Sort> argumentCaptor = forClass(Sort.class);
        verify(this.consumableRepository).findAll(argumentCaptor.capture());
        final Sort sort = argumentCaptor.getValue();

        assertNotNull(sort, "Sort clause unexpectedly null");
        assertEquals(1, sort.toList().size(), "Sort clause size different from expected");
        assertEquals(sortingField, sort.toList().get(0).getProperty(), "Sort clause property different from expected");

        verify(this.consumableRepository, times(1)).findAll(sort);
        verify(this.consumableConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link ConsumableDao#getConsumableById} method.
     */
    @Test
    @DisplayName("Getting a consumable by ID")
    void testGetConsumableById() {
        final ConsumableEntity entity = new ConsumableEntity();
        final Integer id = 0;
        when(this.consumableRepository.findById(id)).thenReturn(Optional.of(entity));

        final Consumable dto = new Consumable();
        when(this.consumableConverter.toDto(entity)).thenReturn(dto);

        assertEquals(dto, this.consumableDao.getConsumableById(id), "Result consumable different from expected");

        verify(this.consumableRepository, times(1)).findById(id);
        verify(this.consumableConverter, times(1)).toDto(entity);
    }

    /**
     * Tests the {@link ConsumableDao#getConsumablesByInterventionId} method.
     */
    @Test
    @DisplayName("Getting all consumables by intervention ID")
    void testGetConsumablesByInterventionId() {
        final Integer interventionId = 0;
        final List<ConsumableEntity> entityList = emptyList();
        when(this.consumableRepository.findAllByInterventionId(interventionId)).thenReturn(entityList);

        final List<Consumable> dtoList = emptyList();
        when(this.consumableConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.consumableDao.getConsumablesByInterventionId(interventionId), "Result list different from expected");

        verify(this.consumableRepository, times(1)).findAllByInterventionId(interventionId);
        verify(this.consumableConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link ConsumableDao#searchConsumables} method.
     */
    @Test
    @DisplayName("Searching consumables")
    void testSearchConsumables() {
        final Consumable body = new Consumable();
        final ConsumableEntity entity = new ConsumableEntity();
        when(this.consumableConverter.toEntity(body)).thenReturn(entity);

        final List<ConsumableEntity> entityList = emptyList();
        when(this.consumableRepository.findAll(any(Example.class))).thenReturn(entityList);

        final List<Consumable> dtoList = emptyList();
        when(this.consumableConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.consumableDao.searchConsumables(body), "Result list different from expected");

        final ArgumentCaptor<Example> argumentCaptor = forClass(Example.class);
        verify(this.consumableRepository).findAll(argumentCaptor.capture());
        final Example example = argumentCaptor.getValue();

        assertNotNull(example, "Example unexpectedly null");
        assertEquals(DEFAULT_MATCHER, example.getMatcher(), "Example matcher different from expected");

        verify(this.consumableRepository, times(1)).findAll(example);
        verify(this.consumableConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link ConsumableDao#saveConsumable} method.
     */
    @Test
    @DisplayName("Saving a consumable")
    void testSaveConsumable() {
        final Consumable body = new Consumable();
        final ConsumableEntity entity = new ConsumableEntity();
        when(this.consumableConverter.toEntity(body)).thenReturn(entity);

        final ConsumableEntity savedEntity = new ConsumableEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.consumableRepository.save(entity)).thenReturn(savedEntity);

        assertEquals(id, this.consumableDao.saveConsumable(body), "Result ID different from expected");

        verify(this.consumableConverter, times(1)).toEntity(body);
        verify(this.consumableRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link ConsumableDao#updateConsumable} method.
     */
    @Test
    @DisplayName("Updating a consumable")
    void testUpdateConsumable() {
        final Consumable body = new Consumable();
        final ConsumableEntity entity = new ConsumableEntity();
        when(this.consumableConverter.toEntity(body)).thenReturn(entity);

        final ConsumableEntity savedEntity = new ConsumableEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.consumableRepository.save(entity)).thenReturn(null);

        this.consumableDao.updateConsumable(body);

        verify(this.consumableConverter, times(1)).toEntity(body);
        verify(this.consumableRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link ConsumableDao#deleteConsumableById} method with an existing consumable.
     */
    @Test
    @DisplayName("Deleting an existing consumable")
    void testDeleteExistingConsumableById() {
        final Integer id = 0;
        when(this.consumableRepository.existsById(id)).thenReturn(true);
        doNothing().when(this.consumableRepository).deleteById(id);

        assertTrue(this.consumableDao.deleteConsumableById(id), "Result different from expected");

        verify(this.consumableRepository, times(1)).existsById(id);
        verify(this.consumableRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the {@link ConsumableDao#deleteConsumableById} method with an inexistant consumable.
     */
    @Test
    @DisplayName("Deleting an inexistant consumable")
    void testDeleteInexistantConsumableById() {
        final Integer id = 0;
        when(this.consumableRepository.existsById(id)).thenReturn(false);

        assertFalse(this.consumableDao.deleteConsumableById(id), "Result different from expected");

        verify(this.consumableRepository, times(1)).existsById(id);
        verify(this.consumableRepository, times(0)).deleteById(id);
    }

}
