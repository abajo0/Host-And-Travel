package hr.algebra.hostandtravel.domain.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private int idReview;
    @NotNull
    private int rating;
    @NotNull
    @NotEmpty
    private String description;

    private LocalDate reviewDate;
    private int hostAndTravelHistoryId;
}
