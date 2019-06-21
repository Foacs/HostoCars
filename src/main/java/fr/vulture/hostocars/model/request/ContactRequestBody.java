package fr.vulture.hostocars.model.request;

import static java.util.Objects.nonNull;

import fr.vulture.hostocars.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Representation of a contact entity with optional fields for web service requests.
 */
@Data
public class ContactRequestBody implements RequestBody {

    private Optional<String> name;
    private Optional<String> nickname;
    private Optional<Long> number;
    private Optional<Boolean> favorite;

    /**
     * Returns true if the name field is not null.
     *
     * @return true if the name field is not null
     */
    public boolean hasMissingMandatoryFields() {
        return ObjectUtils.isAnyNull(name);
    }

    @Override
    public boolean hasNonNullFields() {
        return ObjectUtils.isAnyNonNull(name, nickname, number, favorite);
    }

    @Override
    public Collection<QueryArgument> getQueryArguments() {
        final List<QueryArgument> result = new ArrayList<>();

        if (nonNull(name)) {
            result.add(new QueryArgument("name", name.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(nickname)) {
            result.add(new QueryArgument("nickname", nickname.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(number)) {
            result.add(new QueryArgument("number", number.orElse(null), QueryArgumentType.INTEGER));
        }
        if (nonNull(favorite)) {
            result.add(new QueryArgument("favorite", favorite.orElse(null), QueryArgumentType.BOOLEAN));
        }

        return result;
    }

    @Override
    public String toString() {
        Object[] arguments = new Object[]{
            nonNull(name) && name.isPresent() ? name.get() : null,
            nonNull(nickname) && nickname.isPresent() ? nickname.get() : null,
            nonNull(number) && number.isPresent() ? number.get() : null,
            nonNull(favorite) && favorite.isPresent() ? favorite.get() : null
        };

        return MessageFormatter.arrayFormat("ContactRequestBody { Name: {} | Nickname: {} | Number: {} | Favorite: {} }", arguments).getMessage();
    }

}
