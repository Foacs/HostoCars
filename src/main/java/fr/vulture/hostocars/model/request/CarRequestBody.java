package fr.vulture.hostocars.model.request;

import static java.util.Objects.*;

import fr.vulture.hostocars.model.request.api.QueryArgument;
import fr.vulture.hostocars.model.request.api.QueryArgumentType;
import fr.vulture.hostocars.model.request.api.SearchRequestBody;
import fr.vulture.hostocars.model.request.api.UpdateRequestBody;
import fr.vulture.hostocars.util.ObjectUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Representation of a car entity with optional fields for web service requests.
 */
@Data
public class CarRequestBody implements SearchRequestBody, UpdateRequestBody {

    private Optional<String> owner;
    private Optional<String> registration;
    private Optional<String> brand;
    private Optional<String> model;
    private Optional<String> motorization;
    private Optional<LocalDate> releaseDate;
    private Optional<String> certificate;
    private Optional<String> comments;
    private Optional<String> picture;

    /**
     * Returns true if the given field name exists in the Car table and is relevant.
     *
     * @param fieldName
     *     The field name
     *
     * @return true if the given field name exists in the Car table and is relevant
     */
    public static boolean hasRelevantField(final String fieldName) {
        if (isNull(fieldName) || fieldName.isEmpty()) {
            return false;
        }

        final List<String> fieldNames = Arrays.asList("owner", "registration", "brand", "model", "motorization", "releaseDate");

        return fieldNames.contains(fieldName);
    }

    @Override
    public Collection<QueryArgument> getSearchQueryArguments() {
        final List<QueryArgument> result = new ArrayList<>();

        if (nonNull(owner)) {
            result.add(new QueryArgument("owner", owner.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(registration)) {
            result.add(new QueryArgument("registration", registration.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(brand)) {
            result.add(new QueryArgument("brand", brand.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(model)) {
            result.add(new QueryArgument("model", model.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(motorization)) {
            result.add(new QueryArgument("motorization", motorization.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(releaseDate)) {
            result.add(new QueryArgument("releaseDate", releaseDate.orElse(null), QueryArgumentType.DATE));
        }
        if (nonNull(comments)) {
            result.add(new QueryArgument("comments", comments.orElse(null), QueryArgumentType.TEXT));
        }

        return result;
    }

    @Override
    public boolean hasNonNullSearchFields() {
        return ObjectUtils.isAnyNonNull(owner, registration, brand, model, motorization, releaseDate);
    }

    @Override
    public Collection<QueryArgument> getUpdateQueryArguments() {
        final List<QueryArgument> result = new ArrayList<>();

        if (nonNull(owner)) {
            result.add(new QueryArgument("owner", owner.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(registration)) {
            result.add(new QueryArgument("registration", registration.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(brand)) {
            result.add(new QueryArgument("brand", brand.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(model)) {
            result.add(new QueryArgument("model", model.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(motorization)) {
            result.add(new QueryArgument("motorization", motorization.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(releaseDate)) {
            result.add(new QueryArgument("releaseDate", releaseDate.orElse(null), QueryArgumentType.DATE));
        }
        if (nonNull(certificate)) {
            result.add(new QueryArgument("certificate", certificate.orElse(null), QueryArgumentType.BLOB));
        }
        if (nonNull(comments)) {
            result.add(new QueryArgument("comments", comments.orElse(null), QueryArgumentType.TEXT));
        }
        if (nonNull(picture)) {
            result.add(new QueryArgument("picture", picture.orElse(null), QueryArgumentType.BLOB));
        }

        return result;
    }

    @Override
    public boolean hasNonNullUpdateFields() {
        return ObjectUtils.isAnyNonNull(owner, registration, brand, model, motorization, releaseDate, certificate, comments, picture);
    }

    @Override
    public boolean hasMissingMandatoryFields() {
        return ObjectUtils.isAnyNull(owner, registration);
    }

    @Override
    public String toString() {
        Object[] arguments = new Object[]{
            nonNull(owner) && owner.isPresent() ? owner.get() : null,
            nonNull(registration) && registration.isPresent() ? registration.get() : null,
            nonNull(brand) && brand.isPresent() ? brand.get() : null,
            nonNull(model) && model.isPresent() ? model.get() : null,
            nonNull(motorization) && motorization.isPresent() ? motorization.get() : null,
            nonNull(releaseDate) && releaseDate.isPresent() ? releaseDate.get() : null,
            nonNull(certificate) && certificate.isPresent() ? certificate.get() : null,
            nonNull(comments) && comments.isPresent() ? comments.get() : null,
            nonNull(picture) && picture.isPresent() ? picture.get() : null
        };

        return MessageFormatter.arrayFormat(
            "CarRequestBody { Owner: {} | Registration: {} | Brand: {} | Model: {} | Motorization: {} | Release date: {} | Certificate: {} | Comments: {} | Picture: {} }",
            arguments).getMessage();
    }

}
