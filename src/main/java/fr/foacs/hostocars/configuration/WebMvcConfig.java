package fr.foacs.hostocars.configuration;

import static java.util.Objects.nonNull;

import java.io.IOException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Configuration bean used to fix the Spring URL redirection.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * {@inheritDoc}
     */
    @Override
    @Loggable(debug = true)
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*").addResourceLocations("classpath:/static/").resourceChain(true).addResolver(new CustomPathResourceResolver());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Loggable(debug = true)
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*").exposedHeaders("Location");
    }

    /**
     * Custom implementation of the {@link CustomPathResourceResolver} class.
     */
    static class CustomPathResourceResolver extends PathResourceResolver {

        /**
         * {@inheritDoc}
         */
        @Override
        @Loggable(debug = true)
        @SneakyThrows(IOException.class)
        public final Resource getResource(final String resourcePath, final Resource location) {
            if (nonNull(resourcePath)) {
                final Resource requestedResource = location.createRelative(resourcePath);
                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("static/index.html");
            }

            return new ClassPathResource("static/index.html");
        }

    }

}
