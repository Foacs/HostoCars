package fr.vulture.hostocars.model.converter;

import fr.vulture.hostocars.model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Converter from an SQL query result set to a contact entity
 */
@Component("contactConverter")
public class ContactConverter implements Converter<ResultSet, Contact> {

    @Override
    public Contact from(final ResultSet input) throws SQLException {
        if (input == null) {
            return null;
        }

        final Contact contact = new Contact();
        contact.setId(input.getInt("ID"));
        contact.setName(input.getString("NAME"));
        return contact;
    }

}
