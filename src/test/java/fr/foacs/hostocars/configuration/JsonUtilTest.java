package fr.foacs.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link JsonUtil} class.
 */
@Slf4j
@DisplayName("Exception interceptor")
class JsonUtilTest {

    /**
     * Tests the {@link JsonUtil#writeValueAsJson(Object)} method.
     */
    @Test
    @DisplayName("Write value as JSON")
    void testWriteValueAsJson() {
        assertEquals("{\"bigDecimalField\":1.0,\"bigIntegerField\":1,\"booleanField\":false,\"doubleChild\":1.0,\"floatChild\":1.0,\"hiddenFieldAsBoolean\":true,"
                + "\"hiddenNullFieldAsBoolean\":false,\"integerChild\":1,\"iterableField\":[{\"childField\":\"childValue\"},{\"childField\":\"childValue\"}],\"longChild\":1,"
                + "\"nullField\":null,\"objectField\":{\"childField\":\"childValue\"},\"parentField\":\"parentValue\",\"primitiveBooleanField\":true,\"primitiveDoubleChild\":1.0,"
                + "\"primitiveFloatChild\":1.0,\"primitiveIntegerChild\":1,\"primitiveLongChild\":1,\"primitiveShortField\":1,\"shortField\":1,\"stringField\":\"stringValue\"}",
            JsonUtil.writeValueAsJson(new SerializableClass()), "Result different from expected");
    }

    /**
     * Tests the {@link JsonUtil#writeValueAsJson(Object)} method in case of error.
     */
    @Test
    @DisplayName("Write value as JSON (error case)")
    void testWriteValueAsJsonInError() {
        final var value = mock(NonSerializableClass.class);
        doThrow(UnsupportedOperationException.class).when(value).getField();
        assertEquals("Unable to write as JSON", JsonUtil.writeValueAsJson(value), "Result different from expected");
    }

    /**
     * Serializable parent class for unit tests.
     */
    @Getter
    @Setter
    private static class SerializableParentClass implements Serializable {

        private static final long serialVersionUID = 1L;

        private String parentField = "parentValue";

    }

    /**
     * Serializable class for unit tests.
     */
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    private static class SerializableClass extends SerializableParentClass {

        private static final long serialVersionUID = 2L;

        private String stringField = "stringValue";
        private String nullField;
        private boolean primitiveBooleanField = true;
        private Boolean booleanField = Boolean.FALSE;
        private short primitiveShortField = (short) 1;
        private Short shortField = Short.valueOf("1");
        private int primitiveIntegerChild = 1;
        private Integer integerChild = Integer.valueOf("1");
        private long primitiveLongChild = 1L;
        private Long longChild = Long.valueOf("1");
        private float primitiveFloatChild = 1.0F;
        private Float floatChild = Float.valueOf("1.0");
        private double primitiveDoubleChild = 1.0D;
        private Double doubleChild = Double.valueOf("1.0");
        private BigInteger bigIntegerField = new BigInteger("1");
        private BigDecimal bigDecimalField = new BigDecimal("1.0");
        private SerializableChildClass objectField = new SerializableChildClass();
        private List<SerializableChildClass> iterableField = Arrays.asList(new SerializableChildClass(), new SerializableChildClass());
        @Hide
        private String hiddenField = "hiddenValue";
        @Hide(asBoolean = true)
        private String hiddenFieldAsBoolean = "hiddenFieldAsBoolean";
        @Hide(asBoolean = true)
        private String hiddenNullFieldAsBoolean;

    }

    /**
     * Serializable child class for unit tests.
     */
    @Getter
    @Setter
    private static class SerializableChildClass implements Serializable {

        private static final long serialVersionUID = 3L;

        private String childField = "childValue";

    }

    /**
     * Serializable class that throws an exception for unit tests.
     */
    @Getter
    @Setter
    private static class NonSerializableClass implements Serializable {

        private static final long serialVersionUID = 4L;

        private String field;

    }

}
