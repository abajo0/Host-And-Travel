package hr.algebra.hostandtravel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private int idCity;
    private String cityName;
    private int countryId;
}
