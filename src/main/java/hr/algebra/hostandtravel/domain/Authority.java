package hr.algebra.hostandtravel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    private int idAuthority;
    private String email;
    private String role;
}