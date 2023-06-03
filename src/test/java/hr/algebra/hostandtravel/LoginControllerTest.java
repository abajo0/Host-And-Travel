package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.LoginController;
import hr.algebra.hostandtravel.domain.*;
import hr.algebra.hostandtravel.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private GenderRepository genderRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testOpenLoginPage() throws Exception {
        mockMvc.perform(get("/hostAndTravel/login.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testOpenRegisterPage() throws Exception {
        List<Country> countries = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Gender> genders = new ArrayList<>();

        Mockito.when(countryRepository.getAllCountries()).thenReturn(countries);
        Mockito.when(cityRepository.getAllCities()).thenReturn(cities);
        Mockito.when(genderRepository.getAllGenders()).thenReturn(genders);

        mockMvc.perform(get("/hostAndTravel/register.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("allCountries", countries))
                .andExpect(model().attribute("allCities", cities))
                .andExpect(model().attribute("allGenders", genders));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        Person person = new Person();
        person.setEmail("test@example.com");

        Mockito.when(personRepository.getPersonByEmail(person.getEmail())).thenReturn(null);

        mockMvc.perform(post("/hostAndTravel/registerNewUser.html")
                                .param("email", "test@example.com")
                                .param("hashedPassword", "password")
                                .param("firstName", "John")
                                .param("lastName", "Doe")

                )
                .andExpect(status().isOk())
                .andExpect(view().name("menu"));
    }
}
