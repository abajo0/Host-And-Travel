package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class ProfileController {
    PersonRepository personRepository;

    @GetMapping("profile.html")
    public String openLoginPage(Principal principal) {
        Person person = personRepository.getPersonByEmail(principal.getName());

        System.out.println(person);

        return null;
    }
}