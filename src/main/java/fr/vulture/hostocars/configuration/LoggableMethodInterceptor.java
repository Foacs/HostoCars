package fr.vulture.hostocars.configuration;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.vulture.hostocars.entity.Car;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Interceptor for methods annotated with the {@link Loggable} annotation.
 */
@Slf4j
@Aspect
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggableMethodInterceptor {

    private static final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>(0);
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule("CustomSerializer").addSerializer(Car.class, new CustomSerializer()));

    /**
     * Resolves the intercepted loggable method call.
     *
     * @param joinPoint
     *     The intercepted method call
     *
     * @return the intercepted method result
     */
    @SneakyThrows
    @Around("@annotation(fr.vulture.hostocars.configuration.Loggable)")
    public static Object logMethod(final ProceedingJoinPoint joinPoint) {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final Loggable loggable = method.getAnnotation(Loggable.class);
        final Class<?> methodClass = method.getDeclaringClass();
        final String methodClassName = methodClass.getCanonicalName();
        final Logger logger = loggerMap.getOrDefault(methodClassName, loggerMap.put(methodClassName, LoggerFactory.getLogger(methodClass)));

        if (!loggable.debug() || logger.isDebugEnabled()) {
            final String methodName = method.getName();
            final boolean isTraceEnabled = logger.isTraceEnabled();

            if (isTraceEnabled) {
                if (loggable.inputs()) {
                    final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
                    final Object[] args = joinPoint.getArgs();
                    final Object[] argsToLog = IntStream.range(0, args.length)
                        .mapToObj(i -> Arrays.stream(parametersAnnotations[i]).anyMatch(annotation -> annotation instanceof Hide) ? "Hidden" : args[i])
                        .toArray();

                    logger.trace("{} <= {}", methodName, writeValuesAsJson(argsToLog));
                } else {
                    logger.trace("{} <= Hidden", methodName);
                }
            }

            final long startTime = System.currentTimeMillis();
            final Object result = joinPoint.proceed();
            final long endTime = System.currentTimeMillis();

            if (isTraceEnabled) {
                if (loggable.output()) {
                    if (void.class == method.getReturnType()) {
                        logger.trace("{} => void", methodName);
                    } else {
                        logger.trace("{} => {}", methodName, writeValueAsJson(result));
                    }
                } else {
                    logger.trace("{} => Hidden", methodName);
                }
            }

            if (loggable.debug()) {
                logger.debug("{} [{}ms]", methodName, endTime - startTime);
            } else {
                logger.info("{} [{}ms]", methodName, endTime - startTime);
            }

            return result;
        }

        return joinPoint.proceed();
    }

    /**
     * Writes an object as JSON.
     *
     * @param object
     *     The object to write
     *
     * @return the JSON representation of the given object
     */
    private static String writeValueAsJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final Exception e) {
            return "Unable to write as JSON";
        }
    }

    /**
     * Writes an array of objects as JSON.
     *
     * @param objects
     *     The objects to write
     *
     * @return the JSON representation of the given array of objects
     */
    private static String writeValuesAsJson(final Object[] objects) {
        try {
            return objectMapper.writeValueAsString(objects).replaceAll("\"Hidden\"", "Hidden");
        } catch (final Exception e) {
            return "Unable to write as JSON";
        }
    }

    /**
     * Custom serializer for {@link Serializable} objects.
     */
    private static class CustomSerializer extends StdSerializer<Serializable> {

        private static final long serialVersionUID = -3196299493404776431L;

        /**
         * {@inheritDoc}
         */
        CustomSerializer() {
            this(Serializable.class);
        }

        /**
         * {@inheritDoc}
         */
        CustomSerializer(final Class<Serializable> t) {
            super(t);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SneakyThrows
        public void serialize(final Serializable serializable, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) {
            jsonGenerator.writeStartObject();

            final Class<?> serializableClass = serializable.getClass();
            for (final PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(serializableClass).getPropertyDescriptors()) {
                final Optional<Field> matchedField =
                    Arrays.stream(serializableClass.getDeclaredFields()).filter(field -> field.getName().equals(propertyDescriptor.getName())).findFirst();

                if (matchedField.isPresent()) {
                    final Field field = matchedField.get();
                    final Method method = propertyDescriptor.getReadMethod();

                    if (nonNull(method)) {
                        final Object value = method.invoke(serializable);

                        if (Arrays.stream(field.getAnnotations()).noneMatch(annotation -> annotation instanceof Hide)) {
                            jsonGenerator.writeStringField(propertyDescriptor.getName(), isNull(value) ? null : value.toString());
                        } else {
                            jsonGenerator.writeFieldName(field.getName());

                            if (isNull(value)) {
                                jsonGenerator.writeNull();
                            } else {
                                jsonGenerator.writeRawValue("nonNull");
                            }
                        }
                    }
                }
            }

            jsonGenerator.writeEndObject();
        }

    }

}
