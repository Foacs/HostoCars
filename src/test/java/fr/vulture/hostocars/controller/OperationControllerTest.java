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

import fr.vulture.hostocars.dao.OperationDao;
import fr.vulture.hostocars.dto.Operation;
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
 * Test class for the {@link OperationController} class.
 */
@DisplayName("Operation controller")
@ExtendWith(MockitoExtension.class)
class OperationControllerTest {

    @Mock
    private OperationDao operationDao;

    @InjectMocks
    private OperationController operationController;

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the operations with an OK status")
    final void testGetOperationsWithOkStatus() {
        final List<Operation> operationList = unmodifiableList(singletonList(new Operation()));

        when(this.operationDao.getOperations(null)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.getOperations(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationList, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).getOperations(null);
    }

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the operations with a NO_CONTENT status")
    final void testGetOperationsWithNoContent() {
        final List<Operation> operationList = unmodifiableList(emptyList());

        when(this.operationDao.getOperations(null)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.getOperations(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).getOperations(null);
    }

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the operations with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationsWithInternalError() {
        when(this.operationDao.getOperations(null)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.getOperations(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).getOperations(null);
    }

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#OK} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operations with an OK status and a sorting clause")
    final void testGetSortedOperationsWithOkStatus() {
        final String sortedBy = "";
        final List<Operation> operationList = unmodifiableList(singletonList(new Operation()));

        when(this.operationDao.getOperations(sortedBy)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.getOperations(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationList, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).getOperations(sortedBy);
    }

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#NO_CONTENT} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operations with a NO_CONTENT status and a sorting clause")
    final void testGetSortedOperationsWithNoContent() {
        final String sortedBy = "";
        final List<Operation> operationList = unmodifiableList(emptyList());

        when(this.operationDao.getOperations(sortedBy)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.getOperations(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).getOperations(sortedBy);
    }

    /**
     * Tests the {@link OperationController#getOperations} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operations with an INTERNAL_SERVER_ERROR status and a sorting clause")
    final void testGetSortedOperationsWithInternalError() {
        final String sortedBy = "";

        when(this.operationDao.getOperations(sortedBy)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.getOperations(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).getOperations(sortedBy);
    }

    /**
     * Tests the {@link OperationController#getOperationById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting a operation by its ID with an OK status")
    final void testGetOperationByIdWithOkStatus() {
        final int id = 1;
        final Operation operation = new Operation();

        when(this.operationDao.getOperationById(id)).thenReturn(operation);

        final ResponseEntity<?> response = this.operationController.getOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operation, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).getOperationById(id);
    }

    /**
     * Tests the {@link OperationController#getOperationById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting a operation by its ID with a NO_CONTENT status")
    final void testGetOperationByIdWithNoContent() {
        final int id = 1;

        when(this.operationDao.getOperationById(id)).thenReturn(null);

        final ResponseEntity<?> response = this.operationController.getOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).getOperationById(id);
    }

    /**
     * Tests the {@link OperationController#getOperationById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting a operation by its ID with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationByIdWithInternalError() {
        final int id = 1;

        when(this.operationDao.getOperationById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.getOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).getOperationById(id);
    }

    /**
     * Tests the {@link OperationController#getOperationsByInterventionId} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the operations with a given intervention ID with an OK status")
    final void testGetOperationsByInterventionIdWithOkStatus() {
        final int id = 1;
        final List<Operation> operationList = singletonList(new Operation());

        when(this.operationDao.getOperationsByInterventionId(id)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.getOperationsByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationList, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).getOperationsByInterventionId(id);
    }

    /**
     * Tests the {@link OperationController#getOperationsByInterventionId} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the operations with a given intervention ID with a NO_CONTENT status")
    final void testGetOperationsByInterventionIdWithNoContent() {
        final int id = 1;

        when(this.operationDao.getOperationsByInterventionId(id)).thenReturn(unmodifiableList(emptyList()));

        final ResponseEntity<?> response = this.operationController.getOperationsByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).getOperationsByInterventionId(id);
    }

    /**
     * Tests the {@link OperationController#getOperationsByInterventionId} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the operations with a given intervention ID with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationsByInterventionIdWithInternalError() {
        final int id = 1;

        when(this.operationDao.getOperationsByInterventionId(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.getOperationsByInterventionId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).getOperationsByInterventionId(id);
    }

    /**
     * Tests the {@link OperationController#searchOperations} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Searching for operations with an OK status")
    final void testSearchOperationsWithOkStatus() {
        final Operation body = new Operation();
        final List<Operation> operationList = unmodifiableList(singletonList(new Operation()));

        when(this.operationDao.searchOperations(body)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.searchOperations(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationList, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).searchOperations(body);
    }

    /**
     * Tests the {@link OperationController#searchOperations} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Searching for operations with a NO_CONTENT status")
    final void testSearchOperationsWithNoContent() {
        final Operation body = new Operation();
        final List<Operation> operationList = unmodifiableList(emptyList());

        when(this.operationDao.searchOperations(body)).thenReturn(operationList);

        final ResponseEntity<?> response = this.operationController.searchOperations(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).searchOperations(body);
    }

    /**
     * Tests the {@link OperationController#searchOperations} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Searching for operations with an INTERNAL_SERVER_ERROR status")
    final void testSearchOperationsWithInternalError() {
        final Operation body = new Operation();

        when(this.operationDao.searchOperations(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.searchOperations(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).searchOperations(body);
    }

    /**
     * Tests the {@link OperationController#saveOperation} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Saving a new operation with an OK status")
    final void testSaveOperationWithOkStatus() {
        final Operation body = new Operation();
        final Integer id = 1;

        when(this.operationDao.saveOperation(body)).thenReturn(id);

        final ResponseEntity<?> response = this.operationController.saveOperation(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(id, response.getBody(), "Response body different from expected");

        verify(this.operationDao, times(1)).saveOperation(body);
    }

    /**
     * Tests the {@link OperationController#saveOperation} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Saving a new operation with an INTERNAL_SERVER_ERROR status")
    final void testSaveOperationWithInternalError() {
        final Operation body = new Operation();

        when(this.operationDao.saveOperation(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.saveOperation(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).saveOperation(body);
    }

    /**
     * Tests the {@link OperationController#updateOperation} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Updating a operation with an OK status")
    final void testUpdateOperationWithOkStatus() {
        final Operation body = new Operation();

        final ResponseEntity<?> response = this.operationController.updateOperation(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).updateOperation(body);
    }

    /**
     * Tests the {@link OperationController#updateOperation} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Updating a operation with an INTERNAL_SERVER_ERROR status")
    final void testUpdateOperationWithInternalError() {
        final Operation body = new Operation();

        doThrow(new RuntimeException("")).when(this.operationDao).updateOperation(body);

        final ResponseEntity<?> response = this.operationController.updateOperation(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).updateOperation(body);
    }

    /**
     * Tests the {@link OperationController#deleteOperationById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Deleting a operation by its ID with an OK status")
    final void testDeleteOperationByIdWithOkStatus() {
        final int id = 1;

        when(this.operationDao.deleteOperationById(id)).thenReturn(true);

        final ResponseEntity<?> response = this.operationController.deleteOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).deleteOperationById(id);
    }

    /**
     * Tests the {@link OperationController#deleteOperationById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Deleting a operation by its ID with a NO_CONTENT status")
    final void testDeleteOperationByIdWithNoContent() {
        final int id = 1;

        when(this.operationDao.deleteOperationById(id)).thenReturn(false);

        final ResponseEntity<?> response = this.operationController.deleteOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationDao, times(1)).deleteOperationById(id);
    }

    /**
     * Tests the {@link OperationController#deleteOperationById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Deleting a operation by its ID with an INTERNAL_SERVER_ERROR status")
    final void testDeleteOperationByIdWithInternalError() {
        final int id = 1;

        when(this.operationDao.deleteOperationById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.operationController.deleteOperationById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationDao, times(1)).deleteOperationById(id);
    }

}
