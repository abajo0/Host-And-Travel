package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

@org.springframework.stereotype.Repository
@Primary
@EnableAutoConfiguration
public class PersonRepository implements Repository<Person>{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("FOOD")
                .usingGeneratedKeyColumns("ID");
    }

    public Person getPersonByEmail(String email){
        return null;
    }

    @Override
    public List<Person> getAllEntities() {
        List<Person> personList = jdbcTemplate.query("select * from Person",
                (result,rowNum)->new Person());
        return personList;
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
