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

import fr.vulture.hostocars.dao.InterventionDao;
import fr.vulture.hostocars.dto.Intervention;
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
 * Test class for the {@link InterventionController} class.
 */
@DisplayName("Intervention controller")
@ExtendWith(MockitoExtension.class)
class InterventionControllerTest {

    @Mock
    private InterventionDao interventionDao;

    @InjectMocks
    private InterventionController interventionController;

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the interventions with an OK status")
    final void testGetInterventionsWithOkStatus() {
        final List<Intervention> interventionList = unmodifiableList(singletonList(new Intervention()));

        when(this.interventionDao.getInterventions()).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.getInterventions();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(interventionList, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).getInterventions();
    }

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the interventions with a NO_CONTENT status")
    final void testGetInterventionsWithNoContent() {
        final List<Intervention> interventionList = unmodifiableList(emptyList());

        when(this.interventionDao.getInterventions()).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.getInterventions();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).getInterventions();
    }

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the interventions with an INTERNAL_SERVER_ERROR status")
    final void testGetInterventionsWithInternalError() {
        when(this.interventionDao.getInterventions()).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.getInterventions();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).getInterventions();
    }

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#OK} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the interventions with an OK status and a sorting clause")
    final void testGetSortedInterventionsWithOkStatus() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";
        final List<Intervention> interventionList = unmodifiableList(singletonList(new Intervention()));

        when(this.interventionDao.getInterventions(sortingField1, sortingField2)).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.getInterventions(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(interventionList, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).getInterventions(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#NO_CONTENT} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the interventions with a NO_CONTENT status and a sorting clause")
    final void testGetSortedInterventionsWithNoContent() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";
        final List<Intervention> interventionList = unmodifiableList(emptyList());

        when(this.interventionDao.getInterventions(sortingField1, sortingField2)).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.getInterventions(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).getInterventions(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link InterventionController#getInterventions} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the interventions with an INTERNAL_SERVER_ERROR status and a sorting clause")
    final void testGetSortedInterventionsWithInternalError() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";

        when(this.interventionDao.getInterventions(sortingField1, sortingField2)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.getInterventions(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).getInterventions(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link InterventionController#getInterventionById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting a intervention by its ID with an OK status")
    final void testGetInterventionByIdWithOkStatus() {
        final int id = 1;
        final Intervention intervention = new Intervention();

        when(this.interventionDao.getInterventionById(id)).thenReturn(intervention);

        final ResponseEntity<?> response = this.interventionController.getInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(intervention, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).getInterventionById(id);
    }

    /**
     * Tests the {@link InterventionController#getInterventionById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting a intervention by its ID with a NO_CONTENT status")
    final void testGetInterventionByIdWithNoContent() {
        final int id = 1;

        when(this.interventionDao.getInterventionById(id)).thenReturn(null);

        final ResponseEntity<?> response = this.interventionController.getInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).getInterventionById(id);
    }

    /**
     * Tests the {@link InterventionController#getInterventionById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting a intervention by its ID with an INTERNAL_SERVER_ERROR status")
    final void testGetInterventionByIdWithInternalError() {
        final int id = 1;

        when(this.interventionDao.getInterventionById(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.getInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).getInterventionById(id);
    }

    /**
     * Tests the {@link InterventionController#getInterventionsByCarId} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the interventions with a given car ID with an OK status")
    final void testGetInterventionsByCarIdWithOkStatus() {
        final int id = 1;
        final List<Intervention> interventionList = singletonList(new Intervention());

        when(this.interventionDao.getInterventionsByCarId(id)).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.getInterventionsByCarId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(interventionList, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).getInterventionsByCarId(id);
    }

    /**
     * Tests the {@link InterventionController#getInterventionsByCarId} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the interventions with a given car ID with a NO_CONTENT status")
    final void testGetInterventionsByCarIdWithNoContent() {
        final int id = 1;

        when(this.interventionDao.getInterventionsByCarId(id)).thenReturn(unmodifiableList(emptyList()));

        final ResponseEntity<?> response = this.interventionController.getInterventionsByCarId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).getInterventionsByCarId(id);
    }

    /**
     * Tests the {@link InterventionController#getInterventionsByCarId} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the interventions with a given car ID with an INTERNAL_SERVER_ERROR status")
    final void testGetInterventionsByCarIdWithInternalError() {
        final int id = 1;

        when(this.interventionDao.getInterventionsByCarId(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.getInterventionsByCarId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).getInterventionsByCarId(id);
    }

    /**
     * Tests the {@link InterventionController#searchInterventions} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Searching for interventions with an OK status")
    final void testSearchInterventionsWithOkStatus() {
        final Intervention body = new Intervention();
        final List<Intervention> interventionList = unmodifiableList(singletonList(new Intervention()));

        when(this.interventionDao.searchInterventions(body)).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.searchInterventions(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(interventionList, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).searchInterventions(body);
    }

    /**
     * Tests the {@link InterventionController#searchInterventions} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Searching for interventions with a NO_CONTENT status")
    final void testSearchInterventionsWithNoContent() {
        final Intervention body = new Intervention();
        final List<Intervention> interventionList = unmodifiableList(emptyList());

        when(this.interventionDao.searchInterventions(body)).thenReturn(interventionList);

        final ResponseEntity<?> response = this.interventionController.searchInterventions(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).searchInterventions(body);
    }

    /**
     * Tests the {@link InterventionController#searchInterventions} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Searching for interventions with an INTERNAL_SERVER_ERROR status")
    final void testSearchInterventionsWithInternalError() {
        final Intervention body = new Intervention();

        when(this.interventionDao.searchInterventions(body)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.searchInterventions(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).searchInterventions(body);
    }

    /**
     * Tests the {@link InterventionController#saveIntervention} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Saving a new intervention with an OK status")
    final void testSaveInterventionWithOkStatus() {
        final Intervention body = new Intervention();
        final Integer id = 1;

        when(this.interventionDao.saveIntervention(body)).thenReturn(id);

        final ResponseEntity<?> response = this.interventionController.saveIntervention(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(id, response.getBody(), "Response body different from expected");

        verify(this.interventionDao, times(1)).saveIntervention(body);
    }

    /**
     * Tests the {@link InterventionController#saveIntervention} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Saving a new intervention with an INTERNAL_SERVER_ERROR status")
    final void testSaveInterventionWithInternalError() {
        final Intervention body = new Intervention();

        when(this.interventionDao.saveIntervention(body)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.saveIntervention(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).saveIntervention(body);
    }

    /**
     * Tests the {@link InterventionController#updateIntervention} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Updating a intervention with an OK status")
    final void testUpdateInterventionWithOkStatus() {
        final Intervention body = new Intervention();

        final ResponseEntity<?> response = this.interventionController.updateIntervention(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).updateIntervention(body);
    }

    /**
     * Tests the {@link InterventionController#updateIntervention} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Updating a intervention with an INTERNAL_SERVER_ERROR status")
    final void testUpdateInterventionWithInternalError() {
        final Intervention body = new Intervention();

        doThrow(new RuntimeException("Test")).when(this.interventionDao).updateIntervention(body);

        final ResponseEntity<?> response = this.interventionController.updateIntervention(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).updateIntervention(body);
    }

    /**
     * Tests the {@link InterventionController#deleteInterventionById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Deleting a intervention by its ID with an OK status")
    final void testDeleteInterventionByIdWithOkStatus() {
        final int id = 1;

        when(this.interventionDao.deleteInterventionById(id)).thenReturn(true);

        final ResponseEntity<?> response = this.interventionController.deleteInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).deleteInterventionById(id);
    }

    /**
     * Tests the {@link InterventionController#deleteInterventionById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Deleting a intervention by its ID with a NO_CONTENT status")
    final void testDeleteInterventionByIdWithNoContent() {
        final int id = 1;

        when(this.interventionDao.deleteInterventionById(id)).thenReturn(false);

        final ResponseEntity<?> response = this.interventionController.deleteInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.interventionDao, times(1)).deleteInterventionById(id);
    }

    /**
     * Tests the {@link InterventionController#deleteInterventionById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Deleting a intervention by its ID with an INTERNAL_SERVER_ERROR status")
    final void testDeleteInterventionByIdWithInternalError() {
        final int id = 1;

        when(this.interventionDao.deleteInterventionById(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.interventionController.deleteInterventionById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.interventionDao, times(1)).deleteInterventionById(id);
    }

}
