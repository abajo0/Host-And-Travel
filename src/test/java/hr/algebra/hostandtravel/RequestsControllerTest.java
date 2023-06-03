package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.RequestsController;
import hr.algebra.hostandtravel.domain.Dto.RequestDto;
import hr.algebra.hostandtravel.domain.Person;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestsControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestsController requestsController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenSendRequestPage() {
        // Test data setup
        int hostId = 1;
        String principalEmail = "test@example.com";

        Person traveler = new Person();
        traveler.setIdPerson(2);
        traveler.setEmail(principalEmail);

        Person host = new Person();
        host.setIdPerson(hostId);
        host.setFirstName("John");
        host.setLastName("Doe");
        host.setEmail("testTwo@example.com");

        when(principal.getName()).thenReturn(principalEmail);
        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(traveler);
        when(personRepository.getEntity(hostId)).thenReturn(host);


        // Method invocation
        String result = requestsController.openSendRequestPage(principal, hostId, model);

        // Assertions
        assertEquals("sendRequest.html", result);

        verify(model).addAttribute(eq("requestDto"), any(RequestDto.class));
        verify(model).addAttribute("hostName", "John Doe");
    }

    @Test
    void testOpenSendRequestPageWithSameTravelerAndHost() {
        // Test data setup
        int hostId = 1;
        String principalEmail = "test@example.com";

        Person traveler = new Person();
        traveler.setIdPerson(hostId);
        traveler.setEmail(principalEmail);

        Person host = new Person();
        host.setIdPerson(hostId);
        host.setFirstName("John");
        host.setLastName("Doe");
        host.setEmail(principalEmail);

        when(principal.getName()).thenReturn(principalEmail);
        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(traveler);
        when(personRepository.getEntity(hostId)).thenReturn(host);


        String result = requestsController.openSendRequestPage(principal, hostId, model);

        assertEquals("redirect:/hostAndTravel/mainPage", result);

    }


}
