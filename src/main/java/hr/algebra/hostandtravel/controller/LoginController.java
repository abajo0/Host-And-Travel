package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/hostAndTravel")
@AllArgsConstructor
public class LoginController {
    PersonRepository personRepository;
    @GetMapping("login.html")
    public String openLoginPage() {
        return "login";
    }

    @GetMapping("register.html")
    public String openRegisterPage(){
        return "register";
    }
    @PostMapping("registerNewUser.html")
    public String saveNewFood(Person newUser,Model model, HttpSession session) {
        //this method never reached...

        System.out.println(newUser);
        if(personRepository.getPersonByEmail(newUser.getEmail())!= null){
            //user with that email already exists
            throw new RuntimeException("user already exists");
        }
        newUser.setHashedPassword(PasswordUtil.hashPassword(newUser.getHashedPassword()));
        newUser.setIsActive(true);
        newUser.setHostStatus(false);
        personRepository.saveEntity(newUser);

        return "redirect:/hostAndTravel/login.html";
    }

}