package hr.algebra.hostandtravel.domain;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.Period;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Person {
    private int idPerson;
    @NotNull
    @Size(min = 2, message = "Name needs to be at least 2 characters long")
    private String firstName;
    @NotNull
    @Size(min = 2, message = "Last name needs to be at least 2 characters long")
    private String lastName;
    @NotNull
    @Email
    private String email;

    private Boolean isHosting;
    private Boolean isActive;
    private String aboutMe;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String gender;

    private String city;

    @NotNull
    @Size(min = 2, message = "Password needs to be at least 5 characters long")
    private String hashedPassword;

    public int getAge(){
        return Period.between(birthdate,LocalDate.now()).getYears();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
