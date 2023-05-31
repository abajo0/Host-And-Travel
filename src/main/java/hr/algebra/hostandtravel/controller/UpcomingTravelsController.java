package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.HnTConstants;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class UpcomingTravelsController {

    private PersonRepository personRepository;
    private RequestRepository requestRepository;

    @GetMapping("upcomingTravels.html")
    public String openIncomingEvents(Principal principal, Model model) {
        LocalDate currentDate = LocalDate.now();
        Person person = personRepository.getPersonByEmail(principal.getName());

        List<Request> allPersonRequests = requestRepository.getAllEntities().stream().filter(r -> r.getHost().getIdPerson() == person.getIdPerson() || r.getTraveler().getIdPerson() == person.getIdPerson()).toList();
        List<Request> incomingAcceptedRequests =
                allPersonRequests.stream().filter(r ->
                        r.getStatus().equalsIgnoreCase(HnTConstants.REQUEST_STATUS_ACCEPTED)
                                && (r.getStartDate().isAfter(currentDate) || r.getStartDate().isEqual(currentDate))
                ).toList();

        model.addAttribute("upcomingTravels", incomingAcceptedRequests);
        model.addAttribute("hasUpcomingTravels",!incomingAcceptedRequests.isEmpty());

        return "upcomingTravels.html";
    }
}
