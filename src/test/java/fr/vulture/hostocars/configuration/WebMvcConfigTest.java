package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import fr.vulture.hostocars.configuration.WebMvcConfig.CustomPathResourceResolver;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceResolver;

/**
 * Test class for the {@link WebMvcConfig} class.
 */
@DisplayName("Web MVC config")
class WebMvcConfigTest {

    private static WebMvcConfig webMvcConfig;
    private static CustomPathResourceResolver customPathResourceResolver;

    /**
     * Initializes the {@link WebMvcConfig}.
     */
    @BeforeAll
    static void init() {
        webMvcConfig = new WebMvcConfig();
        customPathResourceResolver = new CustomPathResourceResolver();
    }

    /**
     * Tests the {@link WebMvcConfig#addResourceHandlers(ResourceHandlerRegistry)} method.
     */
    @Test
    @DisplayName("Adding a new resource handler")
    final void testAddResourceHandlers() {
        final ResourceHandlerRegistry registry = Mockito.mock(ResourceHandlerRegistry.class);
        final ResourceHandlerRegistration registryRegistration = Mockito.mock(ResourceHandlerRegistration.class);
        final ResourceChainRegistration chainRegistration = Mockito.mock(ResourceChainRegistration.class);

        Mockito.when(registry.addResourceHandler("/**/*")).thenReturn(registryRegistration);
        Mockito.when(registryRegistration.addResourceLocations("classpath:/static/")).thenReturn(registryRegistration);
        Mockito.when(registryRegistration.resourceChain(true)).thenReturn(chainRegistration);
        Mockito.when(chainRegistration.addResolver(ArgumentMatchers.any(CustomPathResourceResolver.class))).thenReturn(chainRegistration);

        webMvcConfig.addResourceHandlers(registry);

        final ArgumentCaptor<CustomPathResourceResolver> argumentCaptor = ArgumentCaptor.forClass(CustomPathResourceResolver.class);
        Mockito.verify(chainRegistration).addResolver(argumentCaptor.capture());
        final ResourceResolver resourceResolver = argumentCaptor.getValue();

        assertNotNull(resourceResolver, "Added resource resolver unexpectedly null");
        assertSame(CustomPathResourceResolver.class, resourceResolver.getClass(), "Added resource resolver class different from expected");

        Mockito.verify(registry, Mockito.times(1)).addResourceHandler("/**/*");
        Mockito.verify(registryRegistration, Mockito.times(1)).addResourceLocations("classpath:/static/");
        Mockito.verify(registryRegistration, Mockito.times(1)).resourceChain(true);
        Mockito.verify(chainRegistration, Mockito.times(1)).addResolver(resourceResolver);
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
        final Resource resource = Mockito.mock(Resource.class);

        final Resource result = customPathResourceResolver.getResource(null, resource);
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
        final String resourcePath = "";
        final Resource resource = Mockito.mock(Resource.class);
        final Resource requestedResource = Mockito.mock(Resource.class);

        Mockito.when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        Mockito.when(requestedResource.exists()).thenReturn(false);

        final Resource result = customPathResourceResolver.getResource(resourcePath, resource);
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");

        Mockito.verify(resource, Mockito.times(1)).createRelative(resourcePath);
        Mockito.verify(requestedResource, Mockito.times(1)).exists();
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
        final String resourcePath = "";
        final Resource resource = Mockito.mock(Resource.class);
        final Resource requestedResource = Mockito.mock(Resource.class);

        Mockito.when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        Mockito.when(requestedResource.exists()).thenReturn(true);
        Mockito.when(requestedResource.isReadable()).thenReturn(false);

        final Resource result = customPathResourceResolver.getResource(resourcePath, resource);
        assertNotNull(result, "Result object unexpectedly null");
        assertSame(ClassPathResource.class, result.getClass(), "Result object class different from expected");
        assertEquals("static/index.html", ((ClassPathResource) result).getPath(), "Path different from expected");

        Mockito.verify(resource, Mockito.times(1)).createRelative(resourcePath);
        Mockito.verify(requestedResource, Mockito.times(1)).exists();
        Mockito.verify(requestedResource, Mockito.times(1)).isReadable();
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
        final String resourcePath = "";
        final Resource resource = Mockito.mock(Resource.class);
        final Resource requestedResource = Mockito.mock(Resource.class);

        Mockito.when(resource.createRelative(resourcePath)).thenReturn(requestedResource);
        Mockito.when(requestedResource.exists()).thenReturn(true);
        Mockito.when(requestedResource.isReadable()).thenReturn(true);

        final Resource result = customPathResourceResolver.getResource(resourcePath, resource);
        assertEquals(requestedResource, result, "Result object instance different from expected");

        Mockito.verify(resource, Mockito.times(1)).createRelative(resourcePath);
        Mockito.verify(requestedResource, Mockito.times(1)).exists();
        Mockito.verify(requestedResource, Mockito.times(1)).isReadable();
    }

}
