package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.vulture.hostocars.configuration.WebMvcConfig.CustomPathResourceResolver;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

    private final WebMvcConfigurer webMvcConfig = new WebMvcConfig();
    private final CustomPathResourceResolver customPathResourceResolver = new CustomPathResourceResolver();

    /**
     * Tests the {@link WebMvcConfig#addResourceHandlers(ResourceHandlerRegistry)} method.
     */
    @Test
    @DisplayName("Adding a new resource handler")
    final void testAddResourceHandlers() {
        final ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        final ResourceHandlerRegistration registryRegistration = mock(ResourceHandlerRegistration.class);
        final ResourceChainRegistration chainRegistration = mock(ResourceChainRegistration.class);

        when(registry.addResourceHandler("/**/*")).thenReturn(registryRegistration);
        when(registryRegistration.addResourceLocations("classpath:/static/")).thenReturn(registryRegistration);
        when(registryRegistration.resourceChain(true)).thenReturn(chainRegistration);
        when(chainRegistration.addResolver(any(CustomPathResourceResolver.class))).thenReturn(chainRegistration);

        this.webMvcConfig.addResourceHandlers(registry);

        final ArgumentCaptor<CustomPathResourceResolver> argumentCaptor = forClass(CustomPathResourceResolver.class);
        verify(chainRegistration).addResolver(argumentCaptor.capture());
        final ResourceResolver resourceResolver = argumentCaptor.getValue();

        assertNotNull(resourceResolver, "Added resource resolver unexpectedly null");
        assertSame(CustomPathResourceResolver.class, resourceResolver.getClass(), "Added resource resolver class different from expected");

        verify(registry, times(1)).addResourceHandler("/**/*");
        verify(registryRegistration, times(1)).addResourceLocations("classpath:/static/");
        verify(registryRegistration, times(1)).resourceChain(true);
        verify(chainRegistration, times(1)).addResolver(resourceResolver);
    }

    /**
     * Tests the {@link WebMvcConfig#addCorsMappings(CorsRegistry)} method.
     */
    @Test
    @DisplayName("Adding a new CORS mapping")
    final void testAddCorsMappings() {
        this.webMvcConfig.addCorsMappings(mock(CorsRegistry.class));
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with a null resource path.
     *
     * @throws IOException
     *     see {@link CustomPathResourceResolver#getResource(String, Resource)}
     */
    @Test
    @DisplayName("Getting a resource with a null resource path")
    final void testGetResourceWithNullResourcePath() throws IOException {
        final Resource resource = mock(Resource.class);

        final Resource result = this.customPathResourceResolver.getResource(null, resource);
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an inexistant requested resource.
     *
     * @throws IOException
     *     see {@link CustomPathResourceResolver#getResource(String, Resource)}
     */
    @Test
    @DisplayName("Getting a resource with an inexistant requested resource")
    final void testGetResourceWithInexistantRequestedResource() throws IOException {
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);
        final Resource requestedResource = mock(Resource.class);

        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(false);

        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");

        verify(resource, times(1)).createRelative(resourcePath);
        verify(requestedResource, times(1)).exists();
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an unreadable requested resource.
     *
     * @throws IOException
     *     see {@link CustomPathResourceResolver#getResource(String, Resource)}
     */
    @Test
    @DisplayName("Getting a resource with an unreadable requested resource")
    final void testGetResourceWithUnreadableRequestedResource() throws IOException {
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);
        final Resource requestedResource = mock(Resource.class);

        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(true);
        when(requestedResource.isReadable()).thenReturn(false);

        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");

        verify(resource, times(1)).createRelative(resourcePath);
        verify(requestedResource, times(1)).exists();
        verify(requestedResource, times(1)).isReadable();
    }

    /**
     * Tests the {@link CustomPathResourceResolver#getResource(String, Resource)} method with an existent and readable requested resource.
     *
     * @throws IOException
     *     see {@link CustomPathResourceResolver#getResource(String, Resource)}
     */
    @Test
    @DisplayName("Getting a resource with an existent and readable requested resource")
    final void testGetResourceWithExistentAndReadableRequestedResource() throws IOException {
        final String resourcePath = "resourcePath";
        final Resource resource = mock(Resource.class);
        final Resource requestedResource = mock(Resource.class);

        when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        when(requestedResource.exists()).thenReturn(true);
        when(requestedResource.isReadable()).thenReturn(true);

        final Resource result = this.customPathResourceResolver.getResource(resourcePath, resource);
        assertEquals(requestedResource, result, "Result object instance different from expected");

        verify(resource, times(1)).createRelative(resourcePath);
        verify(requestedResource, times(1)).exists();
        verify(requestedResource, times(1)).isReadable();
    }

}
