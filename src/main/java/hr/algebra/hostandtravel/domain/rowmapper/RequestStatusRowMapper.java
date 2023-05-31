package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.Gender;
import hr.algebra.hostandtravel.domain.RequestStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestStatusRowMapper implements RowMapper<RequestStatus> {
    @Override
    public RequestStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RequestStatus(
                rs.getInt("IDRequestStatus"),
                rs.getString("Status"));
    }
}
