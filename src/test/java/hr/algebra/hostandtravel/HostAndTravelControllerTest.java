package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.HostAndTravelController;
import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Review;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class HostAndTravelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @InjectMocks
    private HostAndTravelController hostAndTravelController;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hostAndTravelController).build();
    }

    @Test
    void testOpenLoginPageWithMissingReview() throws Exception {
        // Test data setup
        Person person = new Person();
        person.setIdPerson(1);
        person.setCity("City Name");

        HostAndTravelHistory hostAndTravelHistory = new HostAndTravelHistory();
        hostAndTravelHistory.setHost(new Person());
        hostAndTravelHistory.getHost().setIdPerson(1);
        hostAndTravelHistory.setTraveler(new Person());
        hostAndTravelHistory.getTraveler().setIdPerson(2);
        hostAndTravelHistory.setTravelerReview(null);
        hostAndTravelHistory.setHostReview(null);

        List<HostAndTravelHistory> hostAndTravelHistoryList = new ArrayList<>();
        hostAndTravelHistoryList.add(hostAndTravelHistory);

        when(principal.getName()).thenReturn("test@example.com");
        when(personRepository.getPersonByEmail("test@example.com")).thenReturn(person);
        when(hostAndTravelHistoryRepository.getHostAndTravelHistoryByPersonId(1)).thenReturn(hostAndTravelHistoryList);


        mockMvc.perform(MockMvcRequestBuilders.get("/hostAndTravel/mainPage.html")
                        .principal(principal))
                .andExpect(MockMvcResultMatchers.view().name("mainPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("idPerson", 1))
                .andExpect(MockMvcResultMatchers.model().attribute("cityName", "City Name"))
                .andExpect(MockMvcResultMatchers.model().attribute("missingReview", true))
                .andExpect(MockMvcResultMatchers.model().attributeExists("missingReviewList"));
    }


    @Test
    void testOpenLoginPageWithoutMissingReview() throws Exception {
        // Test data setup
        Person person = new Person();
        person.setIdPerson(1);
        person.setCity("City Name");

        HostAndTravelHistory hostAndTravelHistory = new HostAndTravelHistory();
        hostAndTravelHistory.setHost(new Person());
        hostAndTravelHistory.getHost().setIdPerson(1);
        hostAndTravelHistory.setTraveler(new Person());
        hostAndTravelHistory.getTraveler().setIdPerson(2);
        Review travelerReview = new Review();
        travelerReview.setIdReview(1);
        travelerReview.setDescription("Traveler review");

        Review hostReview = new Review();
        travelerReview.setIdReview(2);
        travelerReview.setDescription("Host review");

        hostAndTravelHistory.setTravelerReview(travelerReview);
        hostAndTravelHistory.setHostReview(hostReview);

        List<HostAndTravelHistory> hostAndTravelHistoryList = new ArrayList<>();
        hostAndTravelHistoryList.add(hostAndTravelHistory);

        when(principal.getName()).thenReturn("test@example.com");
        when(personRepository.getPersonByEmail("test@example.com")).thenReturn(person);
        when(hostAndTravelHistoryRepository.getHostAndTravelHistoryByPersonId(1)).thenReturn(hostAndTravelHistoryList);

        mockMvc.perform(MockMvcRequestBuilders.get("/hostAndTravel/mainPage.html")
                        .principal(principal))
                .andExpect(MockMvcResultMatchers.view().name("mainPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("idPerson", 1))
                .andExpect(MockMvcResultMatchers.model().attribute("cityName", "City Name"))
                .andExpect(MockMvcResultMatchers.model().attribute("missingReview", false));

    }


}
