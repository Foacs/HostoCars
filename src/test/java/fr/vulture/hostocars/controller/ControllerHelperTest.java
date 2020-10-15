package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test class for the {@link ControllerHelper} class.
 */
@DisplayName("Controller helper")
@ExtendWith(MockitoExtension.class)
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
    @DisplayName("Resolve GET response with content")
    void testResolveGetResponseWithContent() {
        final ResponseEntity<Object> result = this.helper.resolveGetResponse(() -> Optional.of(new Object()));

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetResponse} method with no content.
     */
    @Test
    @DisplayName("Resolve GET response with no content")
    void testResolveGetResponseWithNoContent() {
        final ResponseEntity<Object> result = this.helper.resolveGetResponse(Optional::empty);

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetResponse} method in error.
     */
    @Test
    @DisplayName("Resolve GET response in error")
    void testResolveGetResponseInError() {
        assertThrows(NullPointerException.class, () -> this.helper.resolveGetResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method.
     */
    @Test
    @DisplayName("Resolve GET collection response")
    void testResolveGetCollectionResponse() {
        final ResponseEntity<Collection<Object>> result = this.helper.resolveGetCollectionResponse(() -> Collections.singleton(""));

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method with an empty result list.
     */
    @Test
    @DisplayName("Resolve GET collection response with an empty result list")
    void testResolveGetCollectionResponseWithEmptyResultList() {
        final ResponseEntity<Collection<String>> result = this.helper.resolveGetCollectionResponse(Collections::emptyList);

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveGetCollectionResponse} method in error.
     */
    @Test
    @DisplayName("Resolve GET collection response in error")
    void testResolveGetCollectionResponseInError() {
        assertThrows(NullPointerException.class, () -> this.helper.resolveGetCollectionResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePostResponse} method.
     */
    @Test
    @DisplayName("Resolve POST response")
    void testResolvePostResponse() {
        final ResponseEntity result = this.helper.resolvePostResponse(() -> "/location");

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
    @DisplayName("Resolve POST response in error")
    void testResolvePostResponseInError() {
        assertThrows(NullPointerException.class, () -> this.helper.resolvePostResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePutResponse} method.
     */
    @Test
    @DisplayName("Resolve PUT response")
    void testResolvePutResponse() {
        final ResponseEntity result = this.helper.resolvePutResponse(Object::new);

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolvePutResponse} method in error.
     */
    @Test
    @DisplayName("Resolve PUT response in error")
    void testResolvePutResponseInError() {
        assertThrows(NullPointerException.class, () -> this.helper.resolvePutResponse(null), "Expected exception not thrown");
    }

    /**
     * Tests the {@link ControllerHelper#resolveDeleteResponse} method.
     */
    @Test
    @DisplayName("Resolve DELETE response")
    void testResolveDeleteResponse() {
        final ResponseEntity result = this.helper.resolveDeleteResponse(Object::new);

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode(), "Response status different from expected");
    }

    /**
     * Tests the {@link ControllerHelper#resolveDeleteResponse} method in error.
     */
    @Test
    @DisplayName("Resolve DELETE response in error")
    void testResolveDeleteResponseInError() {
        assertThrows(NullPointerException.class, () -> this.helper.resolveDeleteResponse(null), "Expected exception not thrown");
    }

}
