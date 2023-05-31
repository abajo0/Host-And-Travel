package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hostAndTravel")
@AllArgsConstructor
public class HostAndTravelController {
    PersonRepository personRepository;
    HostAndTravelHistoryRepository hostAndTravelHistoryRepository;


    @GetMapping("mainPage.html")
    public String openLoginPage(Principal principal, Model model) {
        Person person = personRepository.getPersonByEmail(principal.getName());
        model.addAttribute("idPerson",person.getIdPerson());
        model.addAttribute("cityName",person.getCity());


        List<HostAndTravelHistory> hostAndTravelHistoryList =  hostAndTravelHistoryRepository.getHostAndTravelHistoryByPersonId(person.getIdPerson());
        List<HostAndTravelHistory> missingReview =  hostAndTravelHistoryList.stream().filter(h -> missingReviewPerson(h,person)).toList();

        model.addAttribute("missingReview",!missingReview.isEmpty());
        model.addAttribute("missingReviewList",missingReview);

        return "mainPage.html";
    }

    private boolean missingReviewPerson(HostAndTravelHistory hostAndTravelHistory,Person person) {
        if(hostAndTravelHistory.getHost().getIdPerson() == person.getIdPerson()){
            return hostAndTravelHistory.getTravelerReview() == null;
        }else if (hostAndTravelHistory.getTraveler().getIdPerson() == person.getIdPerson()){
            return hostAndTravelHistory.getHostReview() == null;
        }

        return false;
    }
}