package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.CityRepository;
import hr.algebra.hostandtravel.repository.CountryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class ProfileController {
    PersonRepository personRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;


    @GetMapping("profile.html")
    public String openProfilePage(Principal principal, Model model, @RequestParam("id")Integer personId) {
        Person person = personRepository.getEntity(personId);

        //true if logged in user  is person
        boolean isPrincipalsProfile = person.getEmail().equalsIgnoreCase(principal.getName());
        model.addAttribute("isPrincipalsProfile",isPrincipalsProfile);


        model.addAttribute("firstName",person.getFirstName());
        model.addAttribute("lastName",person.getLastName());
        model.addAttribute("hostStatus",person.getIsHosting() ? "Hosting" : "Not hosting");
        model.addAttribute("aboutMe",person.getAboutMe());
        model.addAttribute("age",person.getAge());
        model.addAttribute("gender",person.getGender());
        model.addAttribute("city",person.getCity());
        model.addAttribute("personId",person.getIdPerson());

        return "profile.html";
    }

    @GetMapping("editProfile.html")
    public String openEditProfilePage(Principal principal,Model model){
        Person person = personRepository.getPersonByEmail(principal.getName());
        
        model.addAttribute("person", person);
        model.addAttribute("allCountries",countryRepository.getAllCountries());
        model.addAttribute("allCities",cityRepository.getAllCities());
        

        return "editProfile.html";
    }

    @PostMapping("saveProfile.html")
    public String saveProfilePage(Model model, @Valid Person person, Principal principal, BindingResult bindingResult){
        if(!personRepository.updateEntity(person)){
            bindingResult.addError(new ObjectError("globalError", "Unable to save changes"));
        }

        String personId = String.valueOf(person.getIdPerson());
        return "redirect:/hostAndTravel/profile.html?id="+ personId;
    }
}