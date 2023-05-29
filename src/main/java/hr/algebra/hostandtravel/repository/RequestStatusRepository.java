package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.RequestStatus;
import hr.algebra.hostandtravel.domain.rowmapper.RequestStatusRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@EnableAutoConfiguration
public class RequestStatusRepository {

    private static final List<RequestStatus> requestStatsList = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;


    public RequestStatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        if (requestStatsList.isEmpty()) {
            requestStatsList.addAll(jdbcTemplate.query("SELECT * FROM RequestStatus", new RequestStatusRowMapper()));
        }
    }

    public int getRequestStatusIdByName(String status){
        Optional<RequestStatus> requestStatus = requestStatsList.stream().filter(r -> r.getStatus().equalsIgnoreCase(status)).findAny();
        if(requestStatus.isPresent()){
            return requestStatus.get().getIdRequestStatus();
        }else{
            return -1;
        }

    }

    public String getRequestStatusNameById(int id){
        Optional<RequestStatus> requestStatus = requestStatsList.stream().filter(c -> c.getIdRequestStatus() == id).findAny();
        if(requestStatus.isPresent()){
            return requestStatus.get().getStatus();
        }else{
            return null;
        }
    }


    public List<String> getAllRequestStatusNames(){
        return requestStatsList.stream().map(RequestStatus::getStatus).toList();
    }

    public List<RequestStatus> getAllRequestStatuses(){
        return new ArrayList<>(requestStatsList);
    }



}
