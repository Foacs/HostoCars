package fr.foacs.hostocars.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for fields and parameters not to be logged by the {@link LoggableMethodInterceptor}.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Hide {

    /**
     * If the information of the field being null or not should be logged instead of totally hiding it.
     * <br/>
     * <br/>
     * Default value: {@code false}
     *
     * @return if the information of the field being null or not should be logged instead of totally hiding it
     */
    boolean asBoolean() default false;

}
