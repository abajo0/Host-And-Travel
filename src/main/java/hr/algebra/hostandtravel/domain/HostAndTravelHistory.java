package hr.algebra.hostandtravel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostAndTravelHistory {
    private int idHostAndTravelHistory;
    private Person host;
    private Person traveler;
    private Review hostReview;
    private Review travelerReview;
    private LocalDate startDate;
    private LocalDate endDate;
}
