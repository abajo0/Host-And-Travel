package hr.algebra.hostandtravel.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    private static  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private PasswordUtil(){}

    public static void main(String[] args) {
        String pas = "password";
        System.out.println(passwordEncoder.encode(pas));
        System.out.println(passwordEncoder.encode("asdasd"));
        System.out.println(passwordEncoder.encode("password"));
    }

    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }
}
