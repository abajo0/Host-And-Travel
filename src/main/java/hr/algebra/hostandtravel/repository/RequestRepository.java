package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.domain.RequestStatus;
import hr.algebra.hostandtravel.domain.rowmapper.PersonRowMapper;
import hr.algebra.hostandtravel.domain.rowmapper.RequestRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@EnableAutoConfiguration
public class RequestRepository implements hr.algebra.hostandtravel.repository.Repository<Request> {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    private RequestStatusRepository requestStatusRepository;

    private PersonRepository personRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate ;

    public RequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.requestStatusRepository = new RequestStatusRepository(jdbcTemplate);
        this.personRepository = new PersonRepository(jdbcTemplate);

        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Request")
                .usingGeneratedKeyColumns("IDRequest");
    }

    @Override
    public List<Request> getAllEntities() {
        return jdbcTemplate.query("SELECT * FROM Request", new RequestRowMapper(personRepository,requestStatusRepository));
    }

    @Override
    public Request getEntity(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource("idRequest", id);

        List<Request> requests = namedParameterJdbcTemplate.query("SELECT * FROM Request WHERE IDRequest =:idRequest", parameters,new RequestRowMapper(personRepository,requestStatusRepository));
        if(!requests.isEmpty()){
            return requests.get(0);
        }
        return null;
    }

    @Override
    public Boolean updateEntity(Request request) {
        try{
            jdbcTemplate.update(
                    "UPDATE Request set HostID = ?,TravelerID = ?, RequestStatusID = ?, StartDate = ?,EndDate = ?, Message = ?  where IDRequest = ?",
                    request.getHost().getIdPerson(),
                    request.getTraveler().getIdPerson(),
                    requestStatusRepository.getRequestStatusIdByName(request.getStatus()),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getIdRequest());
        }catch (Exception e){
            System.out.println(e);
            return false;}


        return true;
    }

    @Override
    public Request insertEntity(Request request) {
        Map<String, Object> params = new HashMap<>();
        params.put("HostID",request.getHost().getIdPerson());
        params.put("TravelerID", request.getTraveler().getIdPerson());
        params.put("RequestStatusID", requestStatusRepository.getRequestStatusIdByName(request.getStatus()));
        params.put("StartDate", Date.valueOf(request.getStartDate()));
        params.put("EndDate", Date.valueOf(request.getEndDate()));
        params.put("Message",request.getMessage());

        int key = inserter.executeAndReturnKey(params).intValue();
        request.setIdRequest(key);

        return request;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }


    public List<Request> getRequestsByHostId(int idPerson) {
        SqlParameterSource parameters = new MapSqlParameterSource("idPerson", idPerson);
        return namedParameterJdbcTemplate.query("SELECT * FROM Request WHERE HostID =:idPerson", parameters,new RequestRowMapper(personRepository,requestStatusRepository));

    }

    public List<Request> getRequestsByTravelerId(int idPerson){
        SqlParameterSource parameters = new MapSqlParameterSource("idPerson", idPerson);
        return namedParameterJdbcTemplate.query("SELECT * FROM Request WHERE TravelerID =:idPerson", parameters,new RequestRowMapper(personRepository,requestStatusRepository));

    }
}
