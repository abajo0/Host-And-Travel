package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.HostAndTravelHistoryController;
import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HostAndTravelHistoryControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @InjectMocks
    private HostAndTravelHistoryController hostAndTravelHistoryController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenHostAndTravelHistoryPage() {
        int personId = 1;
        Person person = new Person();
        person.setIdPerson(personId);
        person.setFirstName("John");
        person.setLastName("Doe");

        List<HostAndTravelHistory> hostAndTravelHistoryList = new ArrayList<>();

        when(personRepository.getEntity(personId)).thenReturn(person);
        when(hostAndTravelHistoryRepository.getHostAndTravelHistoryByPersonId(personId)).thenReturn(hostAndTravelHistoryList);

        String result = hostAndTravelHistoryController.openHostAndTravelHistoryPage(principal, model, personId);

        assertEquals("hostAndTravelHistory.html", result);

        verify(model).addAttribute("hostAndTravelHistoryList", hostAndTravelHistoryList);
        verify(model).addAttribute("personFullName", "John Doe");
        verify(model).addAttribute("personId", personId);
    }
}
