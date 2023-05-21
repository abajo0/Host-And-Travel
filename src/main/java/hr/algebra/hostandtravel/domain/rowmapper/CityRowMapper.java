package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new City(
                rs.getInt("IDCity"),
                rs.getString("CityName"),
                rs.getInt("CountryID"));
    }
}
