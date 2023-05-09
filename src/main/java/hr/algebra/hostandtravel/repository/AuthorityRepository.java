package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Authority;
import hr.algebra.hostandtravel.domain.rowmapper.AuthorityRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
@EnableAutoConfiguration
public class AuthorityRepository {
    private static final List<Authority> authorityList = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public AuthorityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        if(authorityList.isEmpty()){
            authorityList.addAll(jdbcTemplate.query("SELECT * FROM Authority", new AuthorityRowMapper()));
        }

        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Authority")
                .usingGeneratedKeyColumns("IDAuthority");

    }

    public String getRoleByEmail(String email){
        Optional<Authority> authority = authorityList.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findAny();
        if(authority.isPresent()){
            return authority.get().getRole();
        }else{
            return null;
        }

    }

    public Authority insertAuthority(Authority authority){
        Map<String, Object> params = new HashMap<>();
        params.put("Email", authority.getEmail());
        params.put("Role", authority.getRole());

        int key = inserter.executeAndReturnKey(params).intValue();
        authority.setIdAuthority(key);

        return authority;
    }

}
