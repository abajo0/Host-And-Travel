package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.ReviewController;
import hr.algebra.hostandtravel.domain.Dto.ReviewDto;
import hr.algebra.hostandtravel.domain.HostAndTravelHistory;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Review;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.ReviewRepository;
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

class ReviewControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenNewReviewPage() {
        int hostAndTravelId = 1;
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(2);

        HostAndTravelHistory hostAndTravelHistory = new HostAndTravelHistory();
        hostAndTravelHistory.setIdHostAndTravelHistory(hostAndTravelId);
        hostAndTravelHistory.setHost(person);
        hostAndTravelHistory.setTravelerReview(null);

        when(principal.getName()).thenReturn(principalEmail);
        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);
        when(hostAndTravelHistoryRepository.getEntity(hostAndTravelId)).thenReturn(hostAndTravelHistory);

        // Method invocation
        String result = reviewController.openNewReviewPage(principal, hostAndTravelId, model);

        // Assertions
        assertEquals("newReview.html", result);
        verify(model).addAttribute(eq("review"), any(ReviewDto.class));
    }

    @Test
    void testOpenNewReviewPageWithNullHostAndTravelHistory() {
        // Test data setup
        int hostAndTravelId = 1;
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(2);

        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);
        when(hostAndTravelHistoryRepository.getEntity(hostAndTravelId)).thenReturn(null);
        when(principal.getName()).thenReturn(principalEmail);

        String result = reviewController.openNewReviewPage(principal, hostAndTravelId, model);

        // Assertions
        assertEquals("redirect:/hostAndTravel/mainPage.html", result);
    }

    @Test
    void testOpenNewReviewPageWithAlreadyExistingReview() {
        int hostAndTravelId = 1;
        String principalEmail = "test@example.com";

        Person person = new Person();
        person.setIdPerson(2);

        HostAndTravelHistory hostAndTravelHistory = new HostAndTravelHistory();
        hostAndTravelHistory.setIdHostAndTravelHistory(hostAndTravelId);
        hostAndTravelHistory.setHost(person);
        hostAndTravelHistory.setTravelerReview(new Review());

        when(principal.getName()).thenReturn(principalEmail);
        when(personRepository.getPersonByEmail(principalEmail)).thenReturn(person);
        when(hostAndTravelHistoryRepository.getEntity(hostAndTravelId)).thenReturn(hostAndTravelHistory);

        // Method invocation
        String result = reviewController.openNewReviewPage(principal, hostAndTravelId, model);

        // Assertions
        assertEquals("redirect:/hostAndTravel/mainPage.html", result);
    }


}
