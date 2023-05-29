package hr.algebra.hostandtravel.domain.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.repository.*;
import org.springframework.jdbc.core.RowMapper;

public class RequestRowMapper implements RowMapper<Request> {
    private PersonRepository personRepository;
    private RequestStatusRepository requestStatusRepository;

    public RequestRowMapper(PersonRepository personRepository,RequestStatusRepository requestStatusRepository){
        this.personRepository = personRepository;
        this.requestStatusRepository = requestStatusRepository;
    }

    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person host = personRepository.getEntity(rs.getInt("HostID"));
        Person traveler = personRepository.getEntity(rs.getInt("TravelerID"));
        String requestStatus = requestStatusRepository.getRequestStatusNameById(rs.getInt("RequestStatusID"));
        return new Request(
                rs.getInt("IDRequest"),
                host,
                traveler,
                requestStatus,
                rs.getDate("StartDate").toLocalDate(),
                rs.getDate("EndDate").toLocalDate(),
                rs.getString("Message"));
    };
}
