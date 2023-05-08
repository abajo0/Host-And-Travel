package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Gender;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.CityRepository;
import hr.algebra.hostandtravel.repository.CountryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.generic.ClassGen;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@Controller
@RequestMapping("/hostAndTravel")
@AllArgsConstructor
public class LoginController {
    PersonRepository personRepository;
    CountryRepository countryRepository;
    CityRepository cityRepository;
    @GetMapping("login.html")
    public String openLoginPage() {
        return "login";
    }


    @ModelAttribute("person")
    public Person storeEmptyObject() {
        return new Person();
    }
    @GetMapping("register.html")
    public String openRegisterPage(Model model){
        model.addAttribute("allCountries",countryRepository.getAllCountries());
        model.addAttribute("allCities",cityRepository.getAllCities());

        return "register";
    }
    @PostMapping("registerNewUser.html")
    public String registerNewUser(Model model, @Valid Person person, BindingResult bindingResult) {
        if(personRepository.getPersonByEmail(person.getEmail())!= null){
            //user with that email already exists
            bindingResult.addError(new ObjectError("globalError", "User with that email already exists!"));
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("person", person);
            return "register";
        }


        person.setHashedPassword(PasswordUtil.hashPassword(person.getHashedPassword()));
        person.setIsActive(true);
        person.setHostStatus(false);

        personRepository.insertEntity(person);
        return "menu.html";
    }

}