package fr.foacs.hostocars.configuration;

import java.lang.reflect.Method;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Interceptor for methods annotated with the {@link Loggable} annotation.
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty("spring.profiles.active")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggableMethodInterceptor {

    /**
     * Resolves the intercepted loggable method call.
     *
     * @param joinPoint
     *     The intercepted method call
     *
     * @return the intercepted method result
     */
    @SneakyThrows
    @Around("@annotation(fr.foacs.hostocars.configuration.Loggable)")
    public static Object logMethod(final ProceedingJoinPoint joinPoint) {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final Loggable loggable = method.getAnnotation(Loggable.class);
        final Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        final String methodName = method.getName();

        if (logger.isTraceEnabled()) {
            if (loggable.inputs()) {
                logger.trace("{} <= {}", methodName, JsonUtil.writeValueAsJson(joinPoint.getArgs()));
            } else {
                logger.trace("{} <=", methodName);
            }
        }

        final long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long endTime = System.currentTimeMillis();

        if (logger.isTraceEnabled()) {
            if (void.class == method.getReturnType() || !loggable.output()) {
                logger.trace("{} =>", methodName);
            } else {
                logger.trace("{} => {}", methodName, JsonUtil.writeValueAsJson(result));
            }
        }

        if (loggable.debug()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} [{}ms]", methodName, endTime - startTime);
            }
        } else {
            logger.info("{} [{}ms]", methodName, endTime - startTime);
        }

        return result;
    }

}
