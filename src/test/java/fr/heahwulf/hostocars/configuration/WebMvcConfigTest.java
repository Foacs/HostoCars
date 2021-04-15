package fr.heahwulf.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.heahwulf.hostocars.configuration.WebMvcConfig.CustomPathResourceResolver;
import java.io.IOException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;

/**
 * Test class for the {@link WebMvcConfig} class.
 */
@DisplayName("Web MVC config")
class WebMvcConfigTest {

    private final CustomPathResourceResolver customPathResourceResolver = new CustomPathResourceResolver();

    private final WebMvcConfigurer webMvcConfig = new WebMvcConfig();

    /**
     * Tests the {@link WebMvcConfig#addResourceHandlers} method with a null registry.
     */
    @Test
    @DisplayName("Add resource handlers (null registry)")
    final void testAddResourceHandlersWithNullRegistry() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.webMvcConfig.addResourceHandlers(null));
    }

    /**
     * Tests the {@link WebMvcConfig#addResourceHandlers} method.
     */
    @Test
    @DisplayName("Add resource handlers")
    final void testAddResourceHandlers() {
        // Prepares the inputs
        final ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);

        // Prepares the intermediary results
        final ResourceHandlerRegistration registryRegistration = mock(ResourceHandlerRegistration.class);
        final ResourceChainRegistration chainRegistration = mock(ResourceChainRegistration.class);

        // Mocks the calls
        when(registry.addResourceHandler("/**/*")).thenReturn(registryRegistration);
        when(registryRegistration.addResourceLocations("classpath:/static/")).thenReturn(registryRegistration);
        when(registryRegistration.resourceChain(true)).thenReturn(chainRegistration);
        when(chainRegistration.addResolver(any(CustomPathResourceResolver.class))).thenReturn(chainRegistration);

        // Calls the method
        this.webMvcConfig.addResourceHandlers(registry);

        // Initializes the argument captors
        final ArgumentCaptor<CustomPathResourceResolver> resolverCaptor = forClass(CustomPathResourceResolver.class);

        // Checks the mocks calls
        verify(registry).addResourceHandler("/**/*");
        verify(registryRegistration).addResourceLocations("classpath:/static/");
        verify(registryRegistration).resourceChain(true);
        verify(chainRegistration).addResolver(resolverCaptor.capture());

        // Gets the captured arguments
        final ResourceResolver capturedResolver = resolverCaptor.getValue();

        // Checks the captured arguments
        assertNotNull(capturedResolver, "Added resource resolver unexpectedly null");
        assertSame(CustomPathResourceResolver.class, capturedResolver.getClass(), "Added resource resolver class different from expected");
    }

    /**
     * Tests the {@link WebMvcConfig#addCorsMappings} method.
     */
    @Test
    @DisplayName("Add CORS mappings")
    final void testAddCorsMappings() {
        // Prepares the inputs
        final CorsRegistry registry = mock(CorsRegistry.class);

        // Prepares the intermediary results
        final CorsRegistration registryRegistration = mock(CorsRegistration.class);

        // Mocks the calls
        when(registry.addMapping("/**")).thenReturn(registryRegistration);
        when(registryRegistration.allowedHeaders("*")).thenReturn(registryRegistration);
        when(registryRegistration.exposedHeaders("Location")).thenReturn(registryRegistration);

        // Calls the method
        this.webMvcConfig.addCorsMappings(registry);

        // Checks the mocks calls
        verify(registry).addMapping("/**");
        verify(registryRegistration).allowedHeaders("*");
        verify(registryRegistration).exposedHeaders("Location");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with a null location.
     */
    @Test
    @DisplayName("Get resource (null location)")
    final void testGetResourceWithNullLocation() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.customPathResourceResolver.getResource("resourcePath", null));
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with a null resource path.
     */
    @Test
    @DisplayName("Get resource (null resource path)")
    final void testGetResourceWithNullResourcePath() {
        // Prepares the inputs
        final Resource resource = mock(Resource.class);

        // Calls the method
        final Resource result = this.customPathResourceResolver.getResource(null, resource);

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an inexistant requested resource.
     */
    @Test
    @SneakyThrows
    @DisplayName("Get resource (inexistant requested resource)")
    final void testGetResourceWithInexistantRequestedResource() {
        // Prepares the inputs
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);

        // Prepares the intermediary results
        final Resource requestedResource = mock(Resource.class);

        // Mocks the calls
        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(false);

        // Calls the method
        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);

        // Checks the mocks calls
        verify(resource).createRelative(resourcePath);
        verify(requestedResource).exists();

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an unreadable requested resource.
     */
    @Test
    @SneakyThrows
    @DisplayName("Get resource (unreadable requested resource)")
    final void testGetResourceWithUnreadableRequestedResource() {
        // Prepares the inputs
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);

        // Prepares the intermediary results
        final Resource requestedResource = mock(Resource.class);

        // Mocks the calls
        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(true);
        when(requestedResource.isReadable()).thenReturn(false);

        // Calls the method
        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);

        // Checks the mocks calls
        verify(resource).createRelative(resourcePath);
        verify(requestedResource).exists();
        verify(requestedResource).isReadable();

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an existent and readable requested resource.
     */
    @Test
    @SneakyThrows
    @DisplayName("Get resource (existent and readable requested resource)")
    final void testGetResourceWithExistentAndReadableRequestedResource() {
        // Prepares the inputs
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);

        // Prepares the intermediary results
        final Resource requestedResource = mock(Resource.class);

        // Mocks the calls
        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(true);
        when(requestedResource.isReadable()).thenReturn(true);

        // Calls the method
        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);

        // Checks the mocks calls
        verify(resource).createRelative(resourcePath);
        verify(requestedResource).exists();
        verify(requestedResource).isReadable();

        // Checks the result
        assertEquals(requestedResource, result, "Result object instance different from expected");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method in error.
     */
    @Test
    @SneakyThrows
    @DisplayName("Get resource (error case)")
    final void testGetResourceInError() {
        // Prepares the inputs
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);

        // Prepares the intermediary results
        final String message = "message";
        final IOException exception = new IOException(message);

        // Mocks the calls
        when(resource.createRelative(resourcePath)).thenThrow(exception);

        // Calls the method
        final Exception result = assertThrows(Exception.class, () -> this.customPathResourceResolver.getResource(resourcePath, resource), "Exception unexpectedly not thrown");

        // Checks the mocks calls
        verify(resource).createRelative(resourcePath);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertEquals(message, result.getMessage(), "Message different from expected");
    }

}
