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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
@EnableAutoConfiguration
public class HostAndTravelHistoryRepository implements Repository<HostAndTravelHistory> {
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


    @Override
    public List<HostAndTravelHistory> getAllEntities() {
        return null;
    }

    @Override
    public HostAndTravelHistory getEntity(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource("idHostAndTravelHistory", id);

        List<HostAndTravelHistory> hostAndTravelHistoryList = namedParameterJdbcTemplate.query("SELECT * FROM HostAndTravelHistory WHERE IDSurfHistory =:idHostAndTravelHistory", parameters,new HostAndTravelHistoryRowMapper(personRepository,reviewRepository));
        if(!hostAndTravelHistoryList.isEmpty()){
            return hostAndTravelHistoryList.get(0);
        }
        return null;
    }

    @Override
    public Boolean updateEntity(HostAndTravelHistory entity) {

        Integer hostReviewId = entity.getHostReview() == null ? null : entity.getHostReview().getIdReview();
        Integer travelerReviewId = entity.getTravelerReview() == null ? null : entity.getTravelerReview().getIdReview();
        try{
            jdbcTemplate.update(
                    "UPDATE HostAndTravelHistory set HostID = ?,TravelerID = ?, HostReviewID = ?, TravelerReviewID = ?,StartDate = ?,EndDate = ?  where IDSurfHistory = ?",
                    entity.getHost().getIdPerson(),entity.getTraveler().getIdPerson(),hostReviewId,travelerReviewId,entity.getStartDate(), entity.getEndDate(),entity.getIdHostAndTravelHistory());
        }catch (Exception e){
            System.out.println(e);
            return false;}


        return true;
    }

    @Override
    public HostAndTravelHistory insertEntity(HostAndTravelHistory entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("HostID", entity.getHost().getIdPerson());
        params.put("TravelerID", entity.getTraveler().getIdPerson());
        params.put("StartDate", entity.getStartDate());
        params.put("EndDate", entity.getEndDate());
        params.put("HostReviewID", entity.getHostReview() == null ? null : entity.getHostReview().getIdReview());
        params.put("TravelerReviewID", entity.getTravelerReview() == null ? null : entity.getTravelerReview().getIdReview());
        int key = inserter.executeAndReturnKey(params).intValue();
        entity.setIdHostAndTravelHistory(key);

        return entity;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }
}
