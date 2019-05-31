package fr.vulture.hostocars.model.converter;

import fr.vulture.hostocars.model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Converter from an SQL query result set to a contact entity.
 */
@Component("contactConverter")
public class ContactConverter implements Converter<ResultSet, Contact> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact from(final ResultSet input) throws SQLException {
        if (input == null) {
            return null;
        }

        final Contact contact = new Contact();
        contact.setId(input.getInt("id"));
        contact.setName(input.getString("name"));
        contact.setNickname(input.getString("nickname"));
        contact.setNumber(input.getLong("number"));
        contact.setFavorite(input.getBoolean("favorite"));

        return contact;
    }

}
