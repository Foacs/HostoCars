package fr.vulture.hostocars.model.converter;

import static fr.vulture.hostocars.util.DateUtils.getDateFromResultSet;
import static java.util.Objects.isNull;

import fr.vulture.hostocars.model.Car;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Converter from an SQL query result set to a car entity.
 */
@Component("carConverter")
public class CarConverter {

    /**
     * Extracts a car from a result set.
     *
     * @param resultSet
     *     The result set
     *
     * @return a car entity
     *
     * @throws SQLException
     *     if any of the columns in not found in the result set
     */
    public Car from(final ResultSet resultSet) throws SQLException {
        if (isNull(resultSet)) {
            return null;
        }

        final Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setOwner(resultSet.getString("owner"));
        car.setRegistration(resultSet.getString("registration"));
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setMotorization(resultSet.getString("motorization"));
        car.setReleaseDate(getDateFromResultSet(resultSet, "releaseDate"));
        car.setCertificate(resultSet.getBytes("certificate"));
        car.setComments(resultSet.getString( "comments"));
        car.setPicture(resultSet.getBytes("picture"));

        return car;
    }

}
