package fr.vulture.hostocars.configuration;

import static java.util.Objects.nonNull;

import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Configuration bean used to fix the Spring URL redirection.
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(@NonNull final ResourceHandlerRegistry registry) {
        log.trace("Adding resources to registry");

        registry.addResourceHandler("/**/*")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(new CustomPathResourceResolver());

        log.trace("Resources added to registry");
    }

    /**
     * Custom implementation of the {@link CustomPathResourceResolver} class.
     */
    @Slf4j
    static class CustomPathResourceResolver extends PathResourceResolver {

        /**
         * {@inheritDoc}
         */
        @Override
        public final Resource getResource(final String resourcePath, @NonNull final Resource location) throws IOException {
            log.trace("Getting resource from path : {}", resourcePath);

            if (nonNull(resourcePath)) {
                final Resource requestedResource = location.createRelative(resourcePath);
                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("static/index.html");
            }

            return new ClassPathResource("static/index.html");
        }

    }

}
