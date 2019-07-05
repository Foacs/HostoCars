package fr.vulture.hostocars.util;

import static java.util.Objects.nonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Utility class to manipulate dates.
 */
public class DateUtils {

    /**
     * Default constructor.
     */
    private DateUtils() {
        super();
    }

    /**
     * Retrieves a date from a result set and converts it to a {@code LocalDate} object.
     *
     * @param resultSet
     *     The result set
     * @param columnLabel
     *     The date column label
     *
     * @return a {@code LocalDate} object
     *
     * @throws SQLException
     *     if the queried column doesn't exist in the database
     */
    public static LocalDate getDateFromResultSet(final ResultSet resultSet, final String columnLabel) throws SQLException {
        // Retrieves the date as text from the result set
        final String dateText = resultSet.getString(columnLabel);

        // If it is not null, converts it to a LocalDate object and returns it
        if (nonNull(dateText)) {
            return LocalDate.parse(dateText);
        }

        // Returns null
        return null;
    }

}
