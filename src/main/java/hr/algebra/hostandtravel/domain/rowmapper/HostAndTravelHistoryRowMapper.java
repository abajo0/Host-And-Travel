package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.RequestStatus;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.RequestStatusRepository;
import hr.algebra.hostandtravel.repository.ReviewRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HostAndTravelHistoryRowMapper implements RowMapper<HostAndTravelHistory> {

    private PersonRepository personRepository;
    private ReviewRepository reviewRepository;

    public HostAndTravelHistoryRowMapper(PersonRepository personRepository,ReviewRepository reviewRepository){
        this.personRepository = personRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public HostAndTravelHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HostAndTravelHistory(
                rs.getInt("IDSurfHistory"),
                personRepository.getEntity(rs.getInt("HostID")),
                personRepository.getEntity(rs.getInt("TravelerID")),
                reviewRepository.getEntity(rs.getInt("HostReviewID")),
                reviewRepository.getEntity(rs.getInt("TravelerReviewID")),
                rs.getDate("StartDate").toLocalDate(),
                rs.getDate("EndDate").toLocalDate());
    }
}
