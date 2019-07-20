package fr.vulture.hostocars.util;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to manipulate dates.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    /**
     * Parses a string and converts it into a {@link LocalDate} object.
     *
     * @param dateText
     *     The date string
     *
     * @return a {@link LocalDate} object
     */
    public static LocalDate getDateFromString(final String dateText) {
        if (isNull(dateText)) {
            return null;
        }

        return LocalDate.parse(dateText);
    }

}
