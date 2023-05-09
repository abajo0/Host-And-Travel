package hr.algebra.hostandtravel.domain.rowmapper;


import hr.algebra.hostandtravel.domain.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorityRowMapper implements RowMapper<Authority> {
    @Override
    public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Authority(
                rs.getInt("IDAuthority"),
                rs.getString("Email"),
                rs.getString("Role"));
    }
}