package hr.algebra.hostandtravel.service;

import hr.algebra.hostandtravel.domain.HnTConstants;
import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class ScheduledTaskService {

    private RequestRepository requestRepository;
    private HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @Scheduled(cron = "1 0 0 * * *") //every day at midnight
    public void execute() throws InterruptedException {
        LocalDate today = LocalDate.now();

        List<Request> requestList =  requestRepository.getAllEntities();

        for (Request request : requestList) {
            if(request.getStatus().equals(HnTConstants.REQUEST_STATUS_ACCEPTED) && request.getEndDate().isEqual(today)){
                HostAndTravelHistory hostAndTravelHistory = new HostAndTravelHistory();
                hostAndTravelHistory.setHost(request.getHost());
                hostAndTravelHistory.setTraveler(request.getTraveler());
                hostAndTravelHistory.setEndDate(request.getEndDate());
                hostAndTravelHistory.setStartDate(request.getStartDate());
                hostAndTravelHistoryRepository.insertEntity(hostAndTravelHistory);
            }
        }

    }
}