package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/hostAndTravel")
@AllArgsConstructor
public class HostAndTravelController {
    PersonRepository personRepository;

    @GetMapping("mainPage.html")
    public String openLoginPage(Principal principal, Model model) {
        Person person = personRepository.getPersonByEmail(principal.getName());
        model.addAttribute("idPerson",person.getIdPerson());
        model.addAttribute("cityName",person.getCity());

        return "mainPage.html";
    }
}