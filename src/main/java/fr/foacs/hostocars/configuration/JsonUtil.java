package fr.foacs.hostocars.configuration;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.foacs.hostocars.entity.Car;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import lombok.SneakyThrows;

/**
 * Utility class for writing objects as JSONs.
 */
public final class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule("CustomSerializer").addSerializer(Car.class, new CustomSerializer()));

    /**
     * Default constructor.
     */
    private JsonUtil() {
        // Nothing to do here
    }

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
        private static Optional<Field> matchField(final Class serializableClass, final PropertyDescriptor propertyDescriptor) {
            Optional<Field> result;
            Class<?> currentClass = serializableClass;

            do {
                result = Arrays.stream(currentClass.getDeclaredFields()).filter(field -> field.getName().equals(propertyDescriptor.getName())).findFirst();
                currentClass = currentClass.getSuperclass();
            } while (result.isEmpty() && nonNull(currentClass));

            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SneakyThrows({IllegalAccessException.class, IntrospectionException.class, IOException.class, InvocationTargetException.class})
        public void serialize(final Serializable serializable, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) {
            jsonGenerator.writeStartObject();

            final Class<?> serializableClass = serializable.getClass();
            for (final PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(serializableClass).getPropertyDescriptors()) {
                final Optional<Field> matchedField = matchField(serializableClass, propertyDescriptor);

                if (matchedField.isPresent()) {
                    final Field field = matchedField.get();
                    final Method method = propertyDescriptor.getReadMethod();

                    if (nonNull(method)) {
                        method.setAccessible(true);
                        final Object value = method.invoke(serializable);

                        if (Arrays.stream(field.getAnnotations()).noneMatch(annotation -> annotation instanceof Hide)) {
                            if (isNull(value)) {
                                jsonGenerator.writeFieldName(field.getName());
                                jsonGenerator.writeNull();
                            } else if (value instanceof Iterable) {
                                jsonGenerator.writeArrayFieldStart(field.getName());
                                for (final Object item : (Iterable<?>) value) {
                                    this.serialize((Serializable) item, jsonGenerator, serializerProvider);
                                }
                                jsonGenerator.writeEndArray();
                            } else {
                                jsonGenerator.writeStringField(propertyDescriptor.getName(), value.toString());
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
