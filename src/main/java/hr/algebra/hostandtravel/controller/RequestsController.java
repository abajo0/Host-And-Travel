package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.HnTConstants;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Request;
import hr.algebra.hostandtravel.domain.Dto.RequestDto;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.RequestRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class RequestsController {

    private PersonRepository personRepository;
    private RequestRepository requestRepository;

    @GetMapping("sendRequest.html")
    public String openSendRequestPage(Principal principal, @RequestParam("id") Integer hostId, Model model) {
        Person traveler = personRepository.getPersonByEmail(principal.getName());
        Person host = personRepository.getEntity(hostId);
        System.out.println(traveler);
        System.out.println(host);

        if (traveler.equals(host)) {
            return "redirect:/hostAndTravel/mainPage";
        }

        RequestDto requestDto = new RequestDto();
        requestDto.setTravelerId(traveler.getIdPerson());
        requestDto.setHostId(hostId);
        requestDto.setStartDate(LocalDate.now());
        requestDto.setStatus(HnTConstants.REQUEST_STATUS_PENDING);

        model.addAttribute("requestDto", requestDto);

        model.addAttribute("hostName", host.getFirstName() + " " + host.getLastName());

        return "sendRequest.html";
    }


    @PostMapping("sendRequest.html")
    public String openSendRequestPage(Principal principal, @Valid RequestDto requestDto, BindingResult bindingResult) {
        if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
            bindingResult.addError(new ObjectError("globalError", "End date must be after Start Date "));
        }

        Request request = new Request();
        request.setTraveler(personRepository.getEntity(requestDto.getTravelerId()));
        request.setHost(personRepository.getEntity(requestDto.getHostId()));
        request.setStatus(requestDto.getStatus());
        request.setStartDate(requestDto.getStartDate());
        request.setEndDate(requestDto.getEndDate());
        request.setMessage(requestDto.getMessage());
        requestRepository.insertEntity(request);

        return "mainPage.html";
    }

    @GetMapping("requests.html")
    public String openRequestsPage(Principal principal, Model model) {
        Person person = personRepository.getPersonByEmail(principal.getName());
        List<Request> sentRequests = requestRepository.getRequestsByTravelerId(person.getIdPerson());
        List<Request> receivedRequests = requestRepository.getRequestsByHostId(person.getIdPerson());

        List<Request> pendingSentRequests = sentRequests.stream().filter(r -> r.getStatus().equals(HnTConstants.REQUEST_STATUS_PENDING)).toList();
        List<Request> pendingReceivedRequests = receivedRequests.stream().filter(r -> r.getStatus().equals(HnTConstants.REQUEST_STATUS_PENDING)).toList();



        return "requests.html";
    }

    @PostMapping("acceptRequest.html")
    public String acceptRequest(Principal principal, @RequestParam("id") Integer requestId, Model model) {
        Request request = requestRepository.getEntity(requestId);
        Person loggedInPerson = personRepository.getPersonByEmail(principal.getName());
        if (request.getStatus().equals(HnTConstants.REQUEST_STATUS_PENDING) && request.getHost().getIdPerson() ==loggedInPerson.getIdPerson())  {
            request.setStatus(HnTConstants.REQUEST_STATUS_ACCEPTED);
            requestRepository.updateEntity(request);
        }
        return "redirect:/hostAndTravel/requests.html";
    }
    @PostMapping("declineRequest.html") //TODO
    public String declineRequest(Principal principal, @RequestParam("id") Integer requestId, Model model) {
        Request request = requestRepository.getEntity(requestId);
        Person loggedInPerson = personRepository.getPersonByEmail(principal.getName());
        if (request.getStatus().equals(HnTConstants.REQUEST_STATUS_PENDING) && request.getHost().getIdPerson() ==loggedInPerson.getIdPerson())  {
            request.setStatus(HnTConstants.REQUEST_STATUS_DECLINED);
            requestRepository.updateEntity(request);
        }
        return"redirect:/hostAndTravel/requests.html";
    }

}
