package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.rowmapper.HostAndTravelHistoryRowMapper;
import hr.algebra.hostandtravel.domain.rowmapper.PersonRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

@org.springframework.stereotype.Repository
@EnableAutoConfiguration
public class HostAndTravelHistoryRepository {
    private PersonRepository personRepository;
    private ReviewRepository reviewRepository;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert inserter;

    public HostAndTravelHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        personRepository = new PersonRepository(jdbcTemplate);
        reviewRepository = new ReviewRepository(jdbcTemplate);

        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Person")
                .usingGeneratedKeyColumns("IDPerson");
    }

    public List<HostAndTravelHistory> getHostAndTravelHistoryByPersonId(int personId){
        SqlParameterSource parameters = new MapSqlParameterSource("personId", personId);

        return namedParameterJdbcTemplate.query("SELECT * FROM HostAndTravelHistory WHERE HostID = :personId OR TravelerID =:personId", parameters,new HostAndTravelHistoryRowMapper(personRepository,reviewRepository));
    }



}
