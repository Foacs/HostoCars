package fr.vulture.hostocars.model;

import static java.util.Objects.nonNull;

import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Entity of a contact.
 */
@Data
public class Contact {

    private Integer id;
    private String name;
    private String nickname;
    private Long number;
    private Boolean favorite;
    private byte[] picture;

    @Override
    public String toString() {
        Object[] arguments = new Object[]{
            name,
            nickname,
            number,
            favorite,
            nonNull(picture)
        };

        return MessageFormatter.format("Contact { ID: {} | Name: {} | Nickname: {} | Number: {} | Favorite: {} | Picture: {} }", arguments)
            .getMessage();
    }

}
