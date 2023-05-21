package hr.algebra.hostandtravel.domain.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import hr.algebra.hostandtravel.domain.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderRowMapper implements RowMapper<Gender> {
    @Override
    public Gender mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Gender(
                rs.getInt("IDGender"),
                rs.getString("GenderName"));
    }
}
