package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.PersonRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@Primary
@EnableAutoConfiguration
public class PersonRepository implements Repository<Person>{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    private PersonRowMapper personRowMapper;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("FOOD")
                .usingGeneratedKeyColumns("ID");
        this.personRowMapper = new PersonRowMapper();
    }

    public Person getPersonByEmail(String email){
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE Email = ?", personRowMapper, email);
    }

    @Override
    public List<Person> getAllEntities() {
        return jdbcTemplate.query("SELECT * FROM Person", personRowMapper);
    }

    @Override
    public Person getEntity(int id) {
        return null;
    }

    @Override
    public Person saveEntity(Person entity) {
        return null;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }

}
