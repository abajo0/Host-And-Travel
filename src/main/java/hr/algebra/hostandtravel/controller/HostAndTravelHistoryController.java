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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class HostAndTravelHistoryController {
    PersonRepository personRepository;
    HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @GetMapping("hostAndTravelHistory.html")
    public String openHostAndTravelHistoryPage(Principal principal, Model model, @RequestParam("id")Integer personId) {
        Person person = personRepository.getEntity(personId);
        List<HostAndTravelHistory> hostAndTravelHistoryList = hostAndTravelHistoryRepository.getHostAndTravelHistoryByPersonId(personId);

        model.addAttribute("hostAndTravelHistoryList",hostAndTravelHistoryList);
        model.addAttribute("personFullName",person.getFirstName() + " " + person.getLastName());
        model.addAttribute("personId",personId);

        return "hostAndTravelHistory.html";
    }
}
