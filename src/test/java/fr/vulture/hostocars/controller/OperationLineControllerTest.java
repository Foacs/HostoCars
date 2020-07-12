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

import fr.vulture.hostocars.dao.OperationLineDao;
import fr.vulture.hostocars.dto.OperationLine;
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
 * Test class for the {@link OperationLineController} class.
 */
@DisplayName("Operation line controller")
@ExtendWith(MockitoExtension.class)
class OperationLineControllerTest {

    @Mock
    private OperationLineDao operationLineDao;

    @InjectMocks
    private OperationLineController operationLineController;

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with an OK status")
    final void testGetOperationLinesWithOkStatus() {
        final List<OperationLine> operationLineList = unmodifiableList(singletonList(new OperationLine()));

        when(this.operationLineDao.getOperationLines()).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.getOperationLines();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationLineList, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).getOperationLines();
    }

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with a NO_CONTENT status")
    final void testGetOperationLinesWithNoContent() {
        final List<OperationLine> operationLineList = unmodifiableList(emptyList());

        when(this.operationLineDao.getOperationLines()).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.getOperationLines();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).getOperationLines();
    }

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationLinesWithInternalError() {
        when(this.operationLineDao.getOperationLines()).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.getOperationLines();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).getOperationLines();
    }

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#OK} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operation lines with an OK status and a sorting clause")
    final void testGetSortedOperationLinesWithOkStatus() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";
        final List<OperationLine> operationLineList = unmodifiableList(singletonList(new OperationLine()));

        when(this.operationLineDao.getOperationLines(sortingField1, sortingField2)).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.getOperationLines(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationLineList, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).getOperationLines(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#NO_CONTENT} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operation lines with a NO_CONTENT status and a sorting clause")
    final void testGetSortedOperationLinesWithNoContent() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";
        final List<OperationLine> operationLineList = unmodifiableList(emptyList());

        when(this.operationLineDao.getOperationLines(sortingField1, sortingField2)).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.getOperationLines(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).getOperationLines(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLines} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the operation lines with an INTERNAL_SERVER_ERROR status and a sorting clause")
    final void testGetSortedOperationLinesWithInternalError() {
        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";

        when(this.operationLineDao.getOperationLines(sortingField1, sortingField2)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.getOperationLines(sortingField1, sortingField2);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).getOperationLines(sortingField1, sortingField2);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLineById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting a operation line by its ID with an OK status")
    final void testGetOperationLineByIdWithOkStatus() {
        final int id = 1;
        final OperationLine operationLine = new OperationLine();

        when(this.operationLineDao.getOperationLineById(id)).thenReturn(operationLine);

        final ResponseEntity<?> response = this.operationLineController.getOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationLine, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).getOperationLineById(id);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLineById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting a operation line by its ID with a NO_CONTENT status")
    final void testGetOperationLineByIdWithNoContent() {
        final int id = 1;

        when(this.operationLineDao.getOperationLineById(id)).thenReturn(null);

        final ResponseEntity<?> response = this.operationLineController.getOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).getOperationLineById(id);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLineById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting a operation line by its ID with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationLineByIdWithInternalError() {
        final int id = 1;

        when(this.operationLineDao.getOperationLineById(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.getOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).getOperationLineById(id);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLinesByOperationId} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with a given operation ID with an OK status")
    final void testGetOperationLinesByOperationIdWithOkStatus() {
        final int id = 1;
        final List<OperationLine> operationLineList = singletonList(new OperationLine());

        when(this.operationLineDao.getOperationLinesByOperationId(id)).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.getOperationLinesByOperationId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationLineList, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(id);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLinesByOperationId} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with a given operation ID with a NO_CONTENT status")
    final void testGetOperationLinesByOperationIdWithNoContent() {
        final int id = 1;

        when(this.operationLineDao.getOperationLinesByOperationId(id)).thenReturn(unmodifiableList(emptyList()));

        final ResponseEntity<?> response = this.operationLineController.getOperationLinesByOperationId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(id);
    }

    /**
     * Tests the {@link OperationLineController#getOperationLinesByOperationId} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the operation lines with a given operation ID with an INTERNAL_SERVER_ERROR status")
    final void testGetOperationLinesByOperationIdWithInternalError() {
        final int id = 1;

        when(this.operationLineDao.getOperationLinesByOperationId(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.getOperationLinesByOperationId(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).getOperationLinesByOperationId(id);
    }

    /**
     * Tests the {@link OperationLineController#searchOperationLines} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Searching for operation lines with an OK status")
    final void testSearchOperationLinesWithOkStatus() {
        final OperationLine body = new OperationLine();
        final List<OperationLine> operationLineList = unmodifiableList(singletonList(new OperationLine()));

        when(this.operationLineDao.searchOperationLines(body)).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.searchOperationLines(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(operationLineList, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).searchOperationLines(body);
    }

    /**
     * Tests the {@link OperationLineController#searchOperationLines} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Searching for operation lines with a NO_CONTENT status")
    final void testSearchOperationLinesWithNoContent() {
        final OperationLine body = new OperationLine();
        final List<OperationLine> operationLineList = unmodifiableList(emptyList());

        when(this.operationLineDao.searchOperationLines(body)).thenReturn(operationLineList);

        final ResponseEntity<?> response = this.operationLineController.searchOperationLines(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).searchOperationLines(body);
    }

    /**
     * Tests the {@link OperationLineController#searchOperationLines} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Searching for operation lines with an INTERNAL_SERVER_ERROR status")
    final void testSearchOperationLinesWithInternalError() {
        final OperationLine body = new OperationLine();

        when(this.operationLineDao.searchOperationLines(body)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.searchOperationLines(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).searchOperationLines(body);
    }

    /**
     * Tests the {@link OperationLineController#saveOperationLine} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Saving a new operation line with an OK status")
    final void testSaveOperationLineWithOkStatus() {
        final OperationLine body = new OperationLine();
        final Integer id = 1;

        when(this.operationLineDao.saveOperationLine(body)).thenReturn(id);

        final ResponseEntity<?> response = this.operationLineController.saveOperationLine(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(id, response.getBody(), "Response body different from expected");

        verify(this.operationLineDao, times(1)).saveOperationLine(body);
    }

    /**
     * Tests the {@link OperationLineController#saveOperationLine} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Saving a new operation line with an INTERNAL_SERVER_ERROR status")
    final void testSaveOperationLineWithInternalError() {
        final OperationLine body = new OperationLine();

        when(this.operationLineDao.saveOperationLine(body)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.saveOperationLine(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).saveOperationLine(body);
    }

    /**
     * Tests the {@link OperationLineController#updateOperationLine} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Updating a operation line with an OK status")
    final void testUpdateOperationLineWithOkStatus() {
        final OperationLine body = new OperationLine();

        final ResponseEntity<?> response = this.operationLineController.updateOperationLine(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).updateOperationLine(body);
    }

    /**
     * Tests the {@link OperationLineController#updateOperationLine} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Updating a operation line with an INTERNAL_SERVER_ERROR status")
    final void testUpdateOperationLineWithInternalError() {
        final OperationLine body = new OperationLine();

        doThrow(new RuntimeException("Test")).when(this.operationLineDao).updateOperationLine(body);

        final ResponseEntity<?> response = this.operationLineController.updateOperationLine(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).updateOperationLine(body);
    }

    /**
     * Tests the {@link OperationLineController#deleteOperationLineById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Deleting a operation line by its ID with an OK status")
    final void testDeleteOperationLineByIdWithOkStatus() {
        final int id = 1;

        when(this.operationLineDao.deleteOperationLineById(id)).thenReturn(true);

        final ResponseEntity<?> response = this.operationLineController.deleteOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).deleteOperationLineById(id);
    }

    /**
     * Tests the {@link OperationLineController#deleteOperationLineById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Deleting a operation line by its ID with a NO_CONTENT status")
    final void testDeleteOperationLineByIdWithNoContent() {
        final int id = 1;

        when(this.operationLineDao.deleteOperationLineById(id)).thenReturn(false);

        final ResponseEntity<?> response = this.operationLineController.deleteOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.operationLineDao, times(1)).deleteOperationLineById(id);
    }

    /**
     * Tests the {@link OperationLineController#deleteOperationLineById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Deleting a operation line by its ID with an INTERNAL_SERVER_ERROR status")
    final void testDeleteOperationLineByIdWithInternalError() {
        final int id = 1;

        when(this.operationLineDao.deleteOperationLineById(id)).thenThrow(new RuntimeException("Test"));

        final ResponseEntity<?> response = this.operationLineController.deleteOperationLineById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.operationLineDao, times(1)).deleteOperationLineById(id);
    }

}
