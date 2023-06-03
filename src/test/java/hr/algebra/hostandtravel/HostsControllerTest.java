package hr.algebra.hostandtravel;

import hr.algebra.hostandtravel.controller.HostsController;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class HostsControllerTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private HostsController hostsController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Test
    public void testOpenProfilePage() {
        String cityName = "New York";
        Person loggedInPerson = new Person();
        loggedInPerson.setIdPerson(1);
        List<Person> hostList = new ArrayList<>();

        Mockito.when(principal.getName()).thenReturn("test@example.com");
        Mockito.when(personRepository.getPersonByEmail(Mockito.anyString())).thenReturn(loggedInPerson);
        Mockito.when(personRepository.getHostsByCity(cityName)).thenReturn(hostList);

        String result = hostsController.openProfilePage(principal, model, cityName);

        Mockito.verify(personRepository).getPersonByEmail("test@example.com");
        Mockito.verify(personRepository).getHostsByCity(cityName);

        Assert.assertEquals("hosts.html", result);
    }
}
