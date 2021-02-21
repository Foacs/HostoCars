package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#debug} field at {@code false}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (debug = false)")
    void testLogMethodWithLoggableAtFalse() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(false);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument1", "argument2"});
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#debug} field at {@code true}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (debug = true)")
    void testLogMethodWithLoggableAtTrue() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument1", "argument2"});
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} without the {@code DEBUG} level available.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (DEBUG level unavailable)")
    void testLogMethodWithoutDebugLevelAvailable() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        doReturn(PojoClassImpl.class).when(method).getDeclaringClass();
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable).debug();
        verify(method).getDeclaringClass();
        verify(joinPoint).proceed();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with an unserializable argument.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (unserializable argument)")
    void testLogMethodWithUnserializableArgument() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument1", mock(Object.class)});
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with an unserializable result.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (unserializable result)")
    void testLogMethodWithUnserializableResult() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final Object joinPointResult = mock(Object.class);

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {"argument1", "argument2"});
        when(joinPoint.proceed()).thenReturn(joinPointResult);

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable, times(2)).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} with an error.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (error case)")
    void testLogMethodWithAnError() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String message = "message";
        final Exception exception = new Exception(message);

        // Mocks the calls
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(method.getAnnotation(Loggable.class)).thenReturn(loggable);
        when(loggable.debug()).thenReturn(true);
        when(method.getName()).thenReturn("methodName");
        doReturn(Application.class).when(method).getDeclaringClass();
        when(joinPoint.getArgs()).thenReturn(new Object[] {});
        when(joinPoint.proceed()).thenThrow(exception);

        // Calls the method
        final Exception result = assertThrows(Exception.class, () -> LoggableMethodInterceptor.logMethod(joinPoint), "Exception unexpectedly not thrown");

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(loggable).debug();
        verify(method).getName();
        verify(method).getDeclaringClass();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertEquals(message, result.getMessage(), "Message different from expected");
    }

}
