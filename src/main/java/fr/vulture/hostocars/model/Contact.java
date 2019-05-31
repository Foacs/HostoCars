package fr.vulture.hostocars.model;

import java.text.MessageFormat;

/**
 * Entity of a contact.
 */
public class Contact {

    private Integer id;
    private String name;
    private String nickname;
    private Long number;
    private Boolean favorite;

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

    /**
     * Returns the contact nickname.
     *
     * @return the contact nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the contact nickname.
     *
     * @param nickname
     *     The nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the contact number.
     *
     * @return the contact number
     */
    public Long getNumber() {
        return number;
    }

    /**
     * Sets the contact number.
     *
     * @param number
     *     The number to set
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * Returns if the contact is flagged as favorite.
     *
     * @return if the contact is flagged as favorite
     */
    public Boolean getFavorite() {
        return favorite;
    }

    /**
     * Sets the contact favorite flag.
     *
     * @param favorite
     *     The value to set
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MessageFormat
            .format("Contact[ID: {0}; Name: \"{1}\"; Nickname: \"{2}\"; Number: {3}; Favorite: {4}]", id, name, nickname, number, favorite);
    }

}
