package hr.algebra.hostandtravel.controller;

import hr.algebra.hostandtravel.domain.*;
import hr.algebra.hostandtravel.domain.Dto.ReviewDto;
import hr.algebra.hostandtravel.repository.HostAndTravelHistoryRepository;
import hr.algebra.hostandtravel.repository.PersonRepository;
import hr.algebra.hostandtravel.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@AllArgsConstructor
@Controller
@RequestMapping("/hostAndTravel")
public class ReviewController {

    PersonRepository personRepository;
    ReviewRepository reviewRepository;
    HostAndTravelHistoryRepository hostAndTravelHistoryRepository;

    @GetMapping("newReview.html")
    public String openNewReviewPage(Principal principal, @RequestParam("id") Integer hostAndTravelId, Model model) {
        Person person = personRepository.getPersonByEmail(principal.getName());
        HostAndTravelHistory hostAndTravelHistory = hostAndTravelHistoryRepository.getEntity(hostAndTravelId);
        if(hostAndTravelHistory== null){
            System.out.println("1");
            return "redirect:/hostAndTravel/mainPage.html";
        }
        if(hostAndTravelHistory.getHost().getIdPerson() == person.getIdPerson()){
            System.out.println("2");
            if(hostAndTravelHistory.getTravelerReview() != null){
                System.out.println("2");
                return "redirect:/hostAndTravel/mainPage.html";
            }
        }else if (hostAndTravelHistory.getTraveler().getIdPerson() == person.getIdPerson()){
            System.out.println("3");
            if(hostAndTravelHistory.getHostReview() != null){
                System.out.println("3");
                return "redirect:/hostAndTravel/mainPage.html";
            }
        }


        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewDate(LocalDate.now());
        reviewDto.setReviewDate(LocalDate.now());
        reviewDto.setHostAndTravelHistoryId(hostAndTravelId);
        model.addAttribute("review",reviewDto);
        return "newReview.html";
    }


    @PostMapping("newReview.html")
    public String openNewReviewPage( Model model,Principal principal, @Valid ReviewDto reviewDto,BindingResult bindingResult) {
        Person person = personRepository.getPersonByEmail(principal.getName());
        Review review = new Review();
        review.setDescription(reviewDto.getDescription());
        review.setRating(reviewDto.getRating());
        review.setReviewDate(LocalDate.now());
        Review insertedReview = reviewRepository.insertEntity(review);

        HostAndTravelHistory hostAndTravelHistory =  hostAndTravelHistoryRepository.getEntity(reviewDto.getHostAndTravelHistoryId());
        if(hostAndTravelHistory.getHost().getIdPerson() == person.getIdPerson()){
            hostAndTravelHistory.setTravelerReview(insertedReview);
            hostAndTravelHistoryRepository.updateEntity(hostAndTravelHistory);
        }else if(hostAndTravelHistory.getTraveler().getIdPerson() == person.getIdPerson()){
            hostAndTravelHistory.setHostReview(insertedReview);
            hostAndTravelHistoryRepository.updateEntity(hostAndTravelHistory);
        }

        return "redirect:/hostAndTravel/mainPage.html";
    }
}
