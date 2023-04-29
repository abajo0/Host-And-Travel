package hr.algebra.hostandtravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hostAndTravel")
public class LoginController {

    @GetMapping("login.html")
    public String openLoginPage() {
        return "login";
    }
}