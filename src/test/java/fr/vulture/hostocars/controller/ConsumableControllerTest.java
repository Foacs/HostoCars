package fr.vulture.hostocars.controller;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.ConsumableDao;
import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.pojo.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for the {@link ConsumableController} class.
 */
@DisplayName("Consumable controller")
@ExtendWith(MockitoExtension.class)
class ConsumableControllerTest {

    @Mock
    private ConsumableDao consumableDao;

    @InjectMocks
    private ConsumableController consumableController;

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the consumables with an OK status")
    final void testGetConsumablesWithOkStatus() {
        final List<Consumable> consumableList = unmodifiableList(singletonList(new Consumable()));

        when(this.consumableDao.getConsumables(null)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.getConsumables(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(consumableList, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).getConsumables(null);
    }

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the consumables with a NO_CONTENT status")
    final void testGetConsumablesWithNoContent() {
        final List<Consumable> consumableList = unmodifiableList(emptyList());

        when(this.consumableDao.getConsumables(null)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.getConsumables(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).getConsumables(null);
    }

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the consumables with an INTERNAL_SERVER_ERROR status")
    final void testGetConsumablesWithInternalError() {
        when(this.consumableDao.getConsumables(null)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.getConsumables(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).getConsumables(null);
    }

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#OK} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the consumables with an OK status and a sorting clause")
    final void testGetSortedConsumablesWithOkStatus() {
        final String sortedBy = "";
        final List<Consumable> consumableList = unmodifiableList(singletonList(new Consumable()));

        when(this.consumableDao.getConsumables(sortedBy)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.getConsumables(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(consumableList, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).getConsumables(sortedBy);
    }

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#NO_CONTENT} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the consumables with a NO_CONTENT status and a sorting clause")
    final void testGetSortedConsumablesWithNoContent() {
        final String sortedBy = "";
        final List<Consumable> consumableList = unmodifiableList(emptyList());

        when(this.consumableDao.getConsumables(sortedBy)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.getConsumables(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).getConsumables(sortedBy);
    }

    /**
     * Tests the {@link ConsumableController#getConsumables} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the consumables with an INTERNAL_SERVER_ERROR status and a sorting clause")
    final void testGetSortedConsumablesWithInternalError() {
        final String sortedBy = "";

        when(this.consumableDao.getConsumables(sortedBy)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.getConsumables(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).getConsumables(sortedBy);
    }

    /**
     * Tests the {@link ConsumableController#getConsumableById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting a consumable by its ID with an OK status")
    final void testGetConsumableByIdWithOkStatus() {
        final int id = 1;
        final Consumable consumable = new Consumable();

        when(this.consumableDao.getConsumableById(id)).thenReturn(consumable);

        final ResponseEntity<?> response = this.consumableController.getConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(consumable, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).getConsumableById(id);
    }

    /**
     * Tests the {@link ConsumableController#getConsumableById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting a consumable by its ID with a NO_CONTENT status")
    final void testGetConsumableByIdWithNoContent() {
        final int id = 1;

        when(this.consumableDao.getConsumableById(id)).thenReturn(null);

        final ResponseEntity<?> response = this.consumableController.getConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).getConsumableById(id);
    }

    /**
     * Tests the {@link ConsumableController#getConsumableById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting a consumable by its ID with an INTERNAL_SERVER_ERROR status")
    final void testGetConsumableByIdWithInternalError() {
        final int id = 1;

        when(this.consumableDao.getConsumableById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.getConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).getConsumableById(id);
    }

    /**
     * Tests the {@link ConsumableController#getConsumablesByInterventionId} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the consumables with a given intervention ID with an OK status")
    final void testGetConsumablesByInterventionIdWithOkStatus() {
        final int id = 1;
        final List<Consumable> consumableList = singletonList(new Consumable());

        when(this.consumableDao.getConsumablesByInterventionId(id)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.getConsumablesByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(consumableList, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(id);
    }

    /**
     * Tests the {@link ConsumableController#getConsumablesByInterventionId} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the consumables with a given intervention ID with a NO_CONTENT status")
    final void testGetConsumablesByInterventionIdWithNoContent() {
        final int id = 1;

        when(this.consumableDao.getConsumablesByInterventionId(id)).thenReturn(unmodifiableList(emptyList()));

        final ResponseEntity<?> response = this.consumableController.getConsumablesByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(id);
    }

    /**
     * Tests the {@link ConsumableController#getConsumablesByInterventionId} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the consumables with a given intervention ID with an INTERNAL_SERVER_ERROR status")
    final void testGetConsumablesByInterventionIdWithInternalError() {
        final int id = 1;

        when(this.consumableDao.getConsumablesByInterventionId(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.getConsumablesByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).getConsumablesByInterventionId(id);
    }

    /**
     * Tests the {@link ConsumableController#searchConsumables} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Searching for consumables with an OK status")
    final void testSearchConsumablesWithOkStatus() {
        final Consumable body = new Consumable();
        final List<Consumable> consumableList = unmodifiableList(singletonList(new Consumable()));

        when(this.consumableDao.searchConsumables(body)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.searchConsumables(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(consumableList, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).searchConsumables(body);
    }

    /**
     * Tests the {@link ConsumableController#searchConsumables} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Searching for consumables with a NO_CONTENT status")
    final void testSearchConsumablesWithNoContent() {
        final Consumable body = new Consumable();
        final List<Consumable> consumableList = unmodifiableList(emptyList());

        when(this.consumableDao.searchConsumables(body)).thenReturn(consumableList);

        final ResponseEntity<?> response = this.consumableController.searchConsumables(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).searchConsumables(body);
    }

    /**
     * Tests the {@link ConsumableController#searchConsumables} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Searching for consumables with an INTERNAL_SERVER_ERROR status")
    final void testSearchConsumablesWithInternalError() {
        final Consumable body = new Consumable();

        when(this.consumableDao.searchConsumables(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.searchConsumables(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).searchConsumables(body);
    }

    /**
     * Tests the {@link ConsumableController#saveConsumable} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Saving a new consumable with an OK status")
    final void testSaveConsumableWithOkStatus() {
        final Consumable body = new Consumable();
        final Integer id = 1;

        when(this.consumableDao.saveConsumable(body)).thenReturn(id);

        final ResponseEntity<?> response = this.consumableController.saveConsumable(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(id, response.getBody(), "Response body different from expected");

        verify(this.consumableDao, times(1)).saveConsumable(body);
    }

    /**
     * Tests the {@link ConsumableController#saveConsumable} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Saving a new consumable with an INTERNAL_SERVER_ERROR status")
    final void testSaveConsumableWithInternalError() {
        final Consumable body = new Consumable();

        when(this.consumableDao.saveConsumable(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.saveConsumable(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).saveConsumable(body);
    }

    /**
     * Tests the {@link ConsumableController#updateConsumable} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Updating a consumable with an OK status")
    final void testUpdateConsumableWithOkStatus() {
        final Consumable body = new Consumable();

        final ResponseEntity<?> response = this.consumableController.updateConsumable(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).updateConsumable(body);
    }

    /**
     * Tests the {@link ConsumableController#updateConsumable} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Updating a consumable with an INTERNAL_SERVER_ERROR status")
    final void testUpdateConsumableWithInternalError() {
        final Consumable body = new Consumable();

        doThrow(new RuntimeException("")).when(this.consumableDao).updateConsumable(body);

        final ResponseEntity<?> response = this.consumableController.updateConsumable(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).updateConsumable(body);
    }

    /**
     * Tests the {@link ConsumableController#deleteConsumableById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Deleting a consumable by its ID with an OK status")
    final void testDeleteConsumableByIdWithOkStatus() {
        final int id = 1;

        when(this.consumableDao.deleteConsumableById(id)).thenReturn(true);

        final ResponseEntity<?> response = this.consumableController.deleteConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).deleteConsumableById(id);
    }

    /**
     * Tests the {@link ConsumableController#deleteConsumableById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Deleting a consumable by its ID with a NO_CONTENT status")
    final void testDeleteConsumableByIdWithNoContent() {
        final int id = 1;

        when(this.consumableDao.deleteConsumableById(id)).thenReturn(false);

        final ResponseEntity<?> response = this.consumableController.deleteConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.consumableDao, times(1)).deleteConsumableById(id);
    }

    /**
     * Tests the {@link ConsumableController#deleteConsumableById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Deleting a consumable by its ID with an INTERNAL_SERVER_ERROR status")
    final void testDeleteConsumableByIdWithInternalError() {
        final int id = 1;

        when(this.consumableDao.deleteConsumableById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.consumableController.deleteConsumableById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.consumableDao, times(1)).deleteConsumableById(id);
    }

}
