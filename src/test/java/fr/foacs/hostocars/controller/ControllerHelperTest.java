package fr.foacs.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test class for the {@link ControllerHelper} class.
 */
@DisplayName("Controller helper")
class ControllerHelperTest {

    private final ControllerHelper helper = new ControllerHelper();

    /**
     * Initialization method called before all tests.
     */
    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(this.helper, "serverAddress", "serverAddress");
        ReflectionTestUtils.setField(this.helper, "serverPort", "serverPort");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetResponse} method with content.
     */
    @Test
    @DisplayName("Resolve GET response (with content)")
    void testResolveGetResponseWithContent() {
        // Calls the method
        final ResponseEntity<Object> result = this.helper.resolveGetResponse(() -> Optional.of(new Object()));

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetResponse} method without content.
     */
    @Test
    @DisplayName("Resolve GET response (without content)")
    void testResolveGetResponseWithoutContent() {
        // Calls the method
        final ResponseEntity<Object> result = this.helper.resolveGetResponse(Optional::empty);

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetResponse} method in error.
     */
    @Test
    @DisplayName("Resolve GET response (error case)")
    void testResolveGetResponseInError() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.helper.resolveGetResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method.
     */
    @Test
    @DisplayName("Resolve GET collection response")
    void testResolveGetCollectionResponse() {
        // Calls the method
        final ResponseEntity<Collection<Object>> result = this.helper.resolveGetCollectionResponse(() -> Collections.singleton(""));

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method with an empty result list.
     */
    @Test
    @DisplayName("Resolve GET collection response (empty result list)")
    void testResolveGetCollectionResponseWithEmptyResultList() {
        // Calls the method
        final ResponseEntity<Collection<String>> result = this.helper.resolveGetCollectionResponse(Collections::emptyList);

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method in error.
     */
    @Test
    @DisplayName("Resolve GET collection response (error case)")
    void testResolveGetCollectionResponseInError() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.helper.resolveGetCollectionResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePostResponse} method.
     */
    @Test
    @DisplayName("Resolve POST response")
    void testResolvePostResponse() {
        // Calls the method
        final ResponseEntity result = this.helper.resolvePostResponse(() -> "/location");

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.CREATED, result.getStatusCode(), "Response status different from expected");
        assertNotNull(result.getHeaders(), "Response headers unexpectedly null");
        assertNotNull(result.getHeaders().get("location"), "Response location header unexpectedly null");
        assertEquals(1, result.getHeaders().get("location").size(), "Response location header unexpectedly empty");
        assertEquals("http://serverAddress:serverPort/location", result.getHeaders().get("location").get(0), "Response location header different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePostResponse} method in error.
     */
    @Test
    @DisplayName("Resolve POST response (error case)")
    void testResolvePostResponseInError() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.helper.resolvePostResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePutResponse} method.
     */
    @Test
    @DisplayName("Resolve PUT response")
    void testResolvePutResponse() {
        // Calls the method
        final ResponseEntity result = this.helper.resolvePutResponse(Object::new);

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePutResponse} method in error.
     */
    @Test
    @DisplayName("Resolve PUT response (error case)")
    void testResolvePutResponseInError() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.helper.resolvePutResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolveDeleteResponse} method.
     */
    @Test
    @DisplayName("Resolve DELETE response")
    void testResolveDeleteResponse() {
        // Calls the method
        final ResponseEntity result = this.helper.resolveDeleteResponse(Object::new);

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveDeleteResponse} method in error.
     */
    @Test
    @DisplayName("Resolve DELETE response (error case)")
    void testResolveDeleteResponseInError() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.helper.resolveDeleteResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#restTemplate()} method.
     */
    @Test
    @DisplayName("REST template bean")
    void testRestTemplateBean() {
        // Calls the method
        assertNotNull(this.helper.restTemplate(), "Result unexpectedly not null");
    }

}
