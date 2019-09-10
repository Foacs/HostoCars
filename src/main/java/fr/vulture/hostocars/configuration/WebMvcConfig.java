package fr.vulture.hostocars.configuration;

import static java.util.Objects.nonNull;

import java.io.IOException;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Configuration bean used to fix the Spring URL redirection.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addResourceHandlers(@NotNull final ResourceHandlerRegistry registry) {
        logger.debug("Adding resources to registry");

        registry.addResourceHandler("/**/*")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(new CustomPathResourceResolver());

        logger.debug("Resources added to registry");
    }

    /**
     * Custom implementation of the {@link CustomPathResourceResolver} class.
     */
    static class CustomPathResourceResolver extends PathResourceResolver {

        @Override
        public Resource getResource(@Nullable final String resourcePath, @NotNull final Resource location) throws IOException {
            if (nonNull(resourcePath)) {
                final Resource requestedResource = location.createRelative(resourcePath);
                return requestedResource.exists() && requestedResource.isReadable()
                    ? requestedResource
                    : new ClassPathResource("static/index.html");
            }

            return new ClassPathResource("static/index.html");
        }

    }

}
