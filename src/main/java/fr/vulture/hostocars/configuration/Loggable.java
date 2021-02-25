package fr.vulture.hostocars.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.slf4j.event.Level;

/**
 * Annotation for methods to be logged by the {@link LoggableMethodInterceptor}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    /**
     * If the method has to be logged at {@link Level#DEBUG} level instead of {@link Level#INFO}.
     * <br/>
     * <br/>
     * Default value: {@code false}
     *
     * @return if the method has to be logged at {@link Level#DEBUG} level instead of {@link Level#INFO}
     */
    boolean debug() default false;

    /**
     * If the inputs of the method have to be logged at {@link Level#TRACE} level.
     * <br/>
     * <br/>
     * Default value: {@code true}
     *
     * @return if the inputs of the method have to be logged at {@link Level#TRACE} level
     */
    boolean inputs() default true;

    /**
     * If the output of the method has to be logged at {@link Level#TRACE} level.
     * <br/>
     * <br/>
     * Default value: {@code true}
     *
     * @return if the output of the method has to be logged at {@link Level#TRACE} level
     */
    boolean output() default true;

}
