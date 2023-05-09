package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.rowmapper.PersonRowMapper;
import hr.algebra.hostandtravel.util.DBUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Repository
@Primary
@EnableAutoConfiguration
public class PersonRepository implements Repository<Person>{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    private CityRepository cityRepository;


    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        cityRepository = new CityRepository(jdbcTemplate); //TODO Can this be here?

        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Person")
                .usingGeneratedKeyColumns("IDPerson");
    }

    public Person getPersonByEmail(String email){
        List<Person> persons = jdbcTemplate.query("SELECT * FROM Person WHERE Email = ?", new PersonRowMapper(cityRepository), email);
        if(!persons.isEmpty()){
            return persons.get(0);
        }
        return null;
    }

    @Override
    public List<Person> getAllEntities() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper(cityRepository));
    }

    @Override
    public Person getEntity(int id) {
        return null;
    }

    @Override
    public Boolean updateEntity(Person person) {
       return false;
    }

    @Override
    public Person insertEntity(Person person) {
        Map<String, Object> params = new HashMap<>();
        params.put("FirstName", person.getFirstName());
        params.put("LastName", person.getLastName());
        params.put("Email", person.getEmail());
        params.put("HostStatus", person.getHostStatus());
        params.put("IsActive", person.getIsActive());
        params.put("AboutMe", person.getAboutMe());
        params.put("BirthDate", person.getBirthdate());
        params.put("GenderID", person.getGender().id);
        params.put("CityID", cityRepository.getCityIdByName(person.getCity()));
        params.put("HashedPassword", person.getHashedPassword());

        int key = inserter.executeAndReturnKey(params).intValue();
        person.setIdPerson(key);
        /*if(inserter.executeAndReturnKey(params) instanceof Integer key) { //TODO always false
            person.setIdPerson(key);
            return person;
        }
        else {
            throw new RuntimeException("Invalid key returned after insert!");
        }*/
        return person;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }

}
