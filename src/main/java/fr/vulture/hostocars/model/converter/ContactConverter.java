package fr.vulture.hostocars.model.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Converter from an SQL query result set to a contact entity.
 */
@Component("contactConverter")
public class ContactConverter {

    /**
     * Extracts a contact from a result set.
     *
     * @param resultSet
     *     The result set
     *
     * @return a contact
     *
     * @throws SQLException
     *     if any of the columns in not found in the result set
     */
    public Contact from(final ResultSet resultSet) throws SQLException {
        if (isNull(resultSet)) {
            return null;
        }

        final Contact contact = new Contact();
        contact.setId(resultSet.getInt("id"));
        contact.setName(resultSet.getString("name"));
        contact.setNickname(resultSet.getString("nickname"));
        contact.setNumber(resultSet.getLong("number"));
        contact.setFavorite(resultSet.getBoolean("favorite"));
        contact.setPicture(resultSet.getBytes("picture"));

        return contact;
    }

}
