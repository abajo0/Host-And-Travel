package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.ProfileController;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.CityRepository;
import hr.algebra.hostandtravel.repository.CountryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenProfilePage() {
        int personId = 1;
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(personId);
        person.setEmail(principalEmail);
        person.setIsActive(true);
        person.setIsHosting(true);
        person.setBirthdate(LocalDate.of(1999,01,01));

        when(personRepository.getEntity(personId)).thenReturn(person);
        when(principal.getName()).thenReturn(principalEmail);

        // Method invocation
        String result = profileController.openProfilePage(principal, model, personId);

        // Assertions
        assertEquals("profile.html", result);
        verify(model).addAttribute("isPrincipalsProfile", true);

    }

    @Test
    void testOpenEditProfilePage() {
        int personId = 1;
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(personId);
        person.setEmail(principalEmail);
        person.setIsActive(true);
        person.setIsHosting(true);
        person.setBirthdate(LocalDate.of(1999,01,01));

        when(principal.getName()).thenReturn(principalEmail);
        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);


        String result = profileController.openEditProfilePage(principal, model);

        // assertions
        assertEquals("editProfile.html", result);
        verify(model).addAttribute("person", person);
        verify(model).addAttribute("allCountries", countryRepository.getAllCountries());
        verify(model).addAttribute("allCities", cityRepository.getAllCities());
    }


}
