package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class ReferencesController { //TODO , rename this
    @GetMapping("references.html")
    public String openReferencesPage(Principal principal, Model model, Person person) {
        return null;
    }


}
