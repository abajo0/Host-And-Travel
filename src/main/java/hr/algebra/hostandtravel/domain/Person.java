package hr.algebra.hostandtravel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Person {
    private int idPerson;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean hostStatus;
    private Boolean isActive;
    private String aboutMe;
    private Date birthdate;
    private Gender gender;
    private String city;
    private String hashedPassword;

    public  enum Gender{
        MALE,FEMALE;
    }
}
