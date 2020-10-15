package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.openpojo.reflection.impl.PojoClassImpl;
import fr.vulture.hostocars.Application;
import java.lang.reflect.Method;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link LoggableMethodInterceptor} class.
 */
@DisplayName("Loggable method interceptor")
class LoggableMethodInterceptorTest {

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} with the {@link Loggable#debug} field at {@code false}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method with Loggable.debug = false")
    void testLogMethodWithLoggableAtFalse() {
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(false);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument"});

        final String joinPointResult = "result";
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        assertEquals(joinPointResult, result, "Result different from expected");

        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} with the {@link Loggable#debug} field at {@code true}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method with Loggable.debug = true")
    void testLogMethodWithLoggableAtTrue() {
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument"});

        final String joinPointResult = "result";
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        assertEquals(joinPointResult, result, "Result different from expected");

        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} without the {@code DEBUG} level available.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method without the DEBUG level available")
    void testLogMethodWithoutDebugLevelAvailable() {
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        doReturn(PojoClassImpl.class).when(method).getDeclaringClass();

        final String joinPointResult = "result";
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        assertEquals(joinPointResult, result, "Result different from expected");

        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable).debug();
        verify(method).getDeclaringClass();
        verify(joinPoint).proceed();
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} with unserializable argument.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method with unserializable argument")
    void testLogMethodWithUnserializableArgument() {
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final Object value = mock(Object.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {value});

        final String joinPointResult = "result";
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        assertEquals(joinPointResult, result, "Result different from expected");

        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} with an error.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method with an error")
    void testLogMethodWithAnError() {
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final Object value = mock(Object.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {value});

        final String message = "message";
        final Exception exception = new Exception(message);
        when(joinPoint.proceed()).thenThrow(exception);

        final Exception result = assertThrows(Exception.class, () -> LoggableMethodInterceptor.logMethod(joinPoint), "Exception unexpectedly not thrown");

        assertNotNull(result, "Result unexpectedly null");
        assertEquals(message, result.getMessage(), "Message different from expected");

        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
    }

}
