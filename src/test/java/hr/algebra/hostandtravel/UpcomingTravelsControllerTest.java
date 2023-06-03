package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.UpcomingTravelsController;
import hr.algebra.hostandtravel.domain.HnTConstants;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpcomingTravelsControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private UpcomingTravelsController upcomingTravelsController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenIncomingEventsHasUpcomingTravelsTrue() {
        // Test data setup
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(1);

        List<Request> allPersonRequests = new ArrayList<>();
        Request request = new Request();
        request.setStatus(HnTConstants.REQUEST_STATUS_ACCEPTED);
        request.setStartDate(LocalDate.now().plusDays(5));
        request.setEndDate(LocalDate.now().plusDays(10));
        request.setHost(person);
        allPersonRequests.add(request);


        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);
        when(requestRepository.getAllEntities()).thenReturn(allPersonRequests);
        when(principal.getName()).thenReturn(principalEmail);

        // Method invocation
        String result = upcomingTravelsController.openIncomingEvents(principal, model);

        // Assertions
        assertEquals("upcomingTravels.html", result);
        verify(personRepository).getPersonByEmail(principalEmail);
        verify(requestRepository).getAllEntities();
        verify(model).addAttribute("upcomingTravels", allPersonRequests);
        verify(model).addAttribute("hasUpcomingTravels", true);
    }

    @Test
    void testOpenIncomingEventsHasUpcomingTravelsFalse() {
        // Test data setup
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(1);

        List<Request> allPersonRequests = new ArrayList<>();
        Request requestPending = new Request();
        requestPending.setStatus(HnTConstants.REQUEST_STATUS_PENDING);
        requestPending.setStartDate(LocalDate.now().plusDays(5));
        requestPending.setEndDate(LocalDate.now().plusDays(10));
        requestPending.setHost(person);

        Request oldRequest = new Request();
        oldRequest.setStatus(HnTConstants.REQUEST_STATUS_ACCEPTED);
        oldRequest.setStartDate(LocalDate.now().minusDays(10));
        oldRequest.setEndDate(LocalDate.now().plusDays(5));
        oldRequest.setHost(person);

        allPersonRequests.add(requestPending);
        allPersonRequests.add(oldRequest);


        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);
        when(requestRepository.getAllEntities()).thenReturn(allPersonRequests);
        when(principal.getName()).thenReturn(principalEmail);

        // Method invocation
        String result = upcomingTravelsController.openIncomingEvents(principal, model);

        // Assertions
        assertEquals("upcomingTravels.html", result);
        verify(personRepository).getPersonByEmail(principalEmail);
        verify(requestRepository).getAllEntities();
        verify(model).addAttribute("hasUpcomingTravels", false);
    }


}
