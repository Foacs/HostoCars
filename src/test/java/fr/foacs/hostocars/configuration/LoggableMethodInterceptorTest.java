package fr.foacs.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.lang.reflect.Method;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Test class for the {@link LoggableMethodInterceptor} class.
 */
@DisplayName("Loggable method interceptor")
class LoggableMethodInterceptorTest {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoggableMethodInterceptorTest.class);

    /**
     * Initialization method called before each test.
     */
    @BeforeEach
    void initialize() {
        LOGGER.setLevel(Level.TRACE);
    }

    /**
     * Demolition method called after each test.
     */
    @AfterEach
    void teardown() {
        LOGGER.setLevel(Level.TRACE);
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#inputs} field at {@code false}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (inputs = false)")
    void testLogMethodWithInputsAtFalse() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(false).when(loggable).inputs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).output();
        doReturn(false).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#output} field at {@code false}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (output = false)")
    void testLogMethodWithOutputAtFalse() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(false).when(loggable).output();
        doReturn(false).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with a {@link Void} method return type.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (void method return type)")
    void testLogMethodWithVoidMethodReturnType() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(Void.class).when(method).getReturnType();
        doReturn(false).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).proceed();
        verify(method).getReturnType();
        verify(loggable).debug();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#debug} field at {@code false}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (debug = false)")
    void testLogMethodWithDebugAtFalse() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(new Object[] {"argument1", "argument2"}).when(joinPoint).getArgs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).output();
        doReturn(false).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} method with the {@link Loggable#debug} field at {@code true}.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (debug = true)")
    void testLogMethodWithDebugAtTrue() {
        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(new Object[] {"argument1", "argument2"}).when(joinPoint).getArgs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).output();
        doReturn(true).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

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
        // Set the logger level to INFO
        LOGGER.setLevel(Level.INFO);

        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(joinPoint).proceed();
        verify(loggable).debug();

        // Checks the result
        assertSame(joinPointResult, result, "Result different from expected");
    }

    /**
     * Tests the {@link LoggableMethodInterceptor#logMethod} without the {@code TRACE} level available.
     */
    @Test
    @SneakyThrows
    @DisplayName("Log method (TRACE level unavailable)")
    void testLogMethodWithoutTraceLevelAvailable() {
        // Set the logger level to DEBUG
        LOGGER.setLevel(Level.DEBUG);

        // Prepares the inputs
        final ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Prepares the intermediary results
        final MethodSignature methodSignature = mock(MethodSignature.class);
        final Method method = mock(Method.class);
        final Loggable loggable = mock(Loggable.class);
        final String joinPointResult = "result";

        // Mocks the calls
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(joinPoint).proceed();
        verify(loggable).debug();

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
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(new Object[] {"argument1", mock(Object.class)}).when(joinPoint).getArgs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).output();
        doReturn(true).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

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
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(new Object[] {"argument1", "argument2"}).when(joinPoint).getArgs();
        doReturn(joinPointResult).when(joinPoint).proceed();
        doReturn(true).when(loggable).output();
        doReturn(false).when(loggable).debug();

        // Calls the method
        final Object result = LoggableMethodInterceptor.logMethod(joinPoint);

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(method).getName();
        verify(loggable).inputs();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(loggable).output();
        verify(loggable).debug();

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
        doReturn(methodSignature).when(joinPoint).getSignature();
        doReturn(method).when(methodSignature).getMethod();
        doReturn(loggable).when(method).getAnnotation(Loggable.class);
        doReturn(this.getClass()).when(method).getDeclaringClass();
        doReturn("methodName").when(method).getName();
        doReturn(true).when(loggable).inputs();
        doReturn(new Object[] {"argument1", "argument2"}).when(joinPoint).getArgs();
        doThrow(exception).when(joinPoint).proceed();

        // Calls the method
        final Exception result = assertThrows(Exception.class, () -> LoggableMethodInterceptor.logMethod(joinPoint), "Exception unexpectedly not thrown");

        // Checks the mocks calls
        verify(joinPoint).getSignature();
        verify(methodSignature).getMethod();
        verify(method).getAnnotation(Loggable.class);
        verify(method).getDeclaringClass();
        verify(joinPoint).proceed();

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertEquals(message, result.getMessage(), "Message different from expected");
    }

}
