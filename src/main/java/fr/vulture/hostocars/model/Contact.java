package fr.vulture.hostocars.model;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Entity of a contact.
 */
public class Contact {

    private Integer id;
    private String name;

    /**
     * Default constructor.
     */
    public Contact() {
        super();
    }

    /**
     * Returns the entity ID.
     *
     * @return the entity ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the entity ID.
     *
     * @param id
     *     The ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the contact name.
     *
     * @return the contact name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the contact name.
     *
     * @param name
     *     The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Contact [ID: {0}; name: \"{1}\"]", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return id.equals(contact.id) &&
            name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
