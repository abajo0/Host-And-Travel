package hr.algebra.hostandtravel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private int idReview;
    private int rating;
    private String description;
    private LocalDate reviewDate;
}
