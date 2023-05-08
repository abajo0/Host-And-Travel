package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Country(
                rs.getInt("IDCountry"),
                rs.getString("CountryName"));
    }
}