package fr.vulture.hostocars.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
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
 * Interceptors for methods annotated with the {@link Loggable} annotation.
 */
@Slf4j
@Aspect
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggableMethodInterceptor {

    private static final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>(0);
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
                logger.trace("{} <= {}", methodName, writeValueAsJson(joinPoint.getArgs()));
            }

            final long startTime = System.currentTimeMillis();
            final Object result = joinPoint.proceed();
            final long endTime = System.currentTimeMillis();

            if (isTraceEnabled) {
                logger.trace("{} => {}", methodName, writeValueAsJson(result));
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

}
