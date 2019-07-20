package fr.vulture.hostocars;

import static java.util.Objects.isNull;

import java.security.InvalidParameterException;

/**
 * Enumeration of the different profiles.
 */
public enum ProfileEnum {

    /**
     * The production profile.
     **/
    PROD,
    /**
     * The development profile.
     **/
    DEV;

    /**
     * Returns the corresponding ProfileEnum for the given string, or null if does not correspond to any value.
     *
     * @param str
     *     The string value
     *
     * @return the corresponding ProfileEnum ProfileEnum or null if no value corresponds
     */
    public static ProfileEnum of(String str) {
        if (isNull(str)) {
            throw new InvalidParameterException("No string to process");
        }

        if (PROD.name().equalsIgnoreCase(str)) {
            return PROD;
        }

        if (DEV.name().equalsIgnoreCase(str)) {
            return DEV;
        }

        return null;
    }

}
