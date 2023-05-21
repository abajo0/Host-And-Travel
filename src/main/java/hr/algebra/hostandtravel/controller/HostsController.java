package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.CityRepository;
import hr.algebra.hostandtravel.repository.CountryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hostAndTravel")
@AllArgsConstructor
public class HostsController {
    PersonRepository personRepository;


    @GetMapping("hosts.html")
    public String openProfilePage(Principal principal, Model model,@RequestParam("city") String cityName) {
        List<Person> hostList = personRepository.getHostsByCity(cityName);
        model.addAttribute("hostList",hostList);
        System.out.println(hostList);


        return "hosts.html";
    }
}
