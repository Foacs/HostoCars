package fr.vulture.hostocars.model.request;

import fr.vulture.hostocars.util.ObjectUtils;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Representation of a contact entity with optional fields for web service requests.
 */
public class ContactRequestBody implements RequestBody {

    private Optional<String> name;
    private Optional<String> nickname;
    private Optional<Long> number;
    private Optional<Boolean> favorite;

    /**
     * Default constructor.
     */
    private ContactRequestBody() {
        super();
    }

    /**
     * Returns the request name.
     *
     * @return the request name
     */
    public Optional<String> getName() {
        return name;
    }

    /**
     * Sets the request name.
     *
     * @param name
     *     The name to set
     */
    public void setName(Optional<String> name) {
        this.name = name;
    }

    /**
     * Returns the request nickname.
     *
     * @return the request nickname
     */
    public Optional<String> getNickname() {
        return nickname;
    }

    /**
     * Sets the request nickname.
     *
     * @param nickname
     *     The nickname to set
     */
    public void setNickname(Optional<String> nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the request number.
     *
     * @return the request number
     */
    public Optional<Long> getNumber() {
        return number;
    }

    /**
     * Sets the request number.
     *
     * @param number
     *     The number to set
     */
    public void setNumber(Optional<Long> number) {
        this.number = number;
    }

    /**
     * Returns the request favorite flag.
     *
     * @return the request favorite flag
     */
    public Optional<Boolean> getFavorite() {
        return favorite;
    }

    /**
     * Sets the request favorite flag.
     *
     * @param favorite
     *     The value to set
     */
    public void setFavorite(Optional<Boolean> favorite) {
        this.favorite = favorite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNonNullFields() {
        return ObjectUtils.isAnyNonNull(name, nickname, number, favorite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<QueryArgument> getQueryArguments() {
        final List<QueryArgument> result = new ArrayList<>();

        if (name != null) {
            result.add(new QueryArgument("name", name.orElse(null), QueryArgumentType.TEXT));
        }
        if (nickname != null) {
            result.add(new QueryArgument("nickname", nickname.orElse(null), QueryArgumentType.TEXT));
        }
        if (number != null) {
            result.add(new QueryArgument("number", number.orElse(null), QueryArgumentType.INTEGER));
        }
        if (favorite != null) {
            result.add(new QueryArgument("favorite", favorite.orElse(null), QueryArgumentType.BOOLEAN));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MessageFormat
            .format("ContactRequestBody[Name: {0}; Nickname: {1}; Number: {2}; Favorite: {3}]", name, nickname, number, favorite);
    }

}
