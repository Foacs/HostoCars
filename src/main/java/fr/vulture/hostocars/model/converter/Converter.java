package fr.vulture.hostocars.model.converter;

/**
 * Interface describing the behavior of a converter.
 *
 * @param <I>
 *     The converter input type
 * @param <O>
 *     The converter ouput type
 */
public interface Converter<I, O> {

    /**
     * Converts a object of the converter input type to an object of the output type.
     *
     * @param input
     *     The object to convert
     *
     * @return the converted object
     *
     * @throws Exception
     *     if anything bad happens during the conversion
     */
    O from(final I input) throws Exception;

}
