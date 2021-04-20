package fr.foacs.hostocars.configuration;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * Utility class for writing objects as JSONs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {

    private static final ObjectMapper objectMapper =
        new ObjectMapper().registerModule(new SimpleModule("CustomSerializer").addSerializer(Serializable.class, new CustomSerializer()));

    /**
     * Writes an object as JSON.
     *
     * @param object
     *     The object to write
     *
     * @return the JSON representation of the given object
     */
    public static String writeValueAsJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
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
         * Tries to match the property corresponding to the given descriptor with the fields of the given serializable class or its super classes.
         *
         * @param serializableClass
         *     The serializable class
         * @param propertyDescriptor
         *     The property descriptor
         *
         * @return the optional matched field
         */
        private static Optional<Field> matchField(final Class<?> serializableClass, final PropertyDescriptor propertyDescriptor) {
            Optional<Field> result;
            var currentClass = serializableClass;

            do {
                result = Arrays.stream(currentClass.getDeclaredFields()).filter(field -> field.getName().equals(propertyDescriptor.getName())).findFirst();
                currentClass = currentClass.getSuperclass();
            } while (result.isEmpty() && nonNull(currentClass));

            return result;
        }

        /**
         * Writes the given number using the given JSON generator.
         *
         * @param jsonGenerator
         *     The JSON generator to use
         * @param value
         *     The value to write
         *
         * @throws IOException
         *     see {@link JsonGenerator}
         */
        private static void writeNumber(final JsonGenerator jsonGenerator, final Number value) throws IOException {
            final Class<?> valueClass = value.getClass();

            if (Short.TYPE == valueClass || Short.class == valueClass) {
                jsonGenerator.writeNumber((Short) value);
            } else if (Integer.TYPE == valueClass || Integer.class == valueClass) {
                jsonGenerator.writeNumber((Integer) value);
            } else if (Long.TYPE == valueClass || Long.class == valueClass) {
                jsonGenerator.writeNumber((Long) value);
            } else if (Float.TYPE == valueClass || Float.class == valueClass) {
                jsonGenerator.writeNumber((Float) value);
            } else if (Double.TYPE == valueClass || Double.class == valueClass) {
                jsonGenerator.writeNumber((Double) value);
            } else if (BigInteger.class == valueClass) {
                jsonGenerator.writeNumber((BigInteger) value);
            } else if (BigDecimal.class == valueClass) {
                jsonGenerator.writeNumber((BigDecimal) value);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SneakyThrows({IllegalAccessException.class, IntrospectionException.class, IOException.class, InvocationTargetException.class})
        public void serialize(final Serializable serializable, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) {
            jsonGenerator.writeStartObject();

            final Class<?> serializableClass = serializable.getClass();
            for (final var propertyDescriptor : Introspector.getBeanInfo(serializableClass).getPropertyDescriptors()) {
                final var matchedField = matchField(serializableClass, propertyDescriptor);

                if (matchedField.isPresent()) {
                    final var field = matchedField.get();
                    final var method = propertyDescriptor.getReadMethod();

                    if (nonNull(method)) {
                        method.setAccessible(true);
                        final var value = method.invoke(serializable);

                        if (Arrays.stream(field.getAnnotations()).noneMatch(Hide.class::isInstance)) {
                            if (isNull(value)) {
                                jsonGenerator.writeFieldName(field.getName());
                                jsonGenerator.writeNull();
                            } else if (value instanceof Iterable) {
                                jsonGenerator.writeArrayFieldStart(field.getName());
                                for (final Object item : (Iterable<?>) value) {
                                    this.serialize((Serializable) item, jsonGenerator, serializerProvider);
                                }
                                jsonGenerator.writeEndArray();
                            } else if (value instanceof Boolean) {
                                jsonGenerator.writeFieldName(field.getName());
                                jsonGenerator.writeBoolean((Boolean) value);
                            } else if (value instanceof Number) {
                                jsonGenerator.writeFieldName(field.getName());
                                writeNumber(jsonGenerator, (Number) value);
                            } else if (value instanceof String) {
                                jsonGenerator.writeStringField(propertyDescriptor.getName(), value.toString());
                            } else {
                                jsonGenerator.writeFieldName(field.getName());
                                jsonGenerator.writeObject(value);
                            }
                        } else if (field.getAnnotation(Hide.class).asBoolean()) {
                            jsonGenerator.writeFieldName(field.getName());
                            jsonGenerator.writeBoolean(nonNull(value));
                        }
                    }
                }
            }

            jsonGenerator.writeEndObject();
        }

    }

}
