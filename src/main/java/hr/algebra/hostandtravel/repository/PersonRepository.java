package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.rowmapper.PersonRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Repository
@Primary
@EnableAutoConfiguration
public class PersonRepository implements Repository<Person>{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate ;
    private SimpleJdbcInsert inserter;

    private CityRepository cityRepository;
    private GenderRepository genderRepository;


    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        cityRepository = new CityRepository(jdbcTemplate);
        genderRepository = new GenderRepository(jdbcTemplate);

        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Person")
                .usingGeneratedKeyColumns("IDPerson");
    }

    public Person getPersonByEmail(String email){
        List<Person> persons = jdbcTemplate.query("SELECT * FROM Person WHERE Email = ?", new PersonRowMapper(cityRepository,genderRepository), email);
        if(!persons.isEmpty()){
            return persons.get(0);
        }
        return null;
    }

    @Override
    public List<Person> getAllEntities() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper(cityRepository,genderRepository));
    }

    public List<Person> getHostsByCity(String cityName){
        int cityId = cityRepository.getCityIdByName(cityName);
        SqlParameterSource parameters = new MapSqlParameterSource("cityId", cityId);


        return namedParameterJdbcTemplate.query("SELECT * FROM Person WHERE HostStatus = 1 AND CityID =:cityId", parameters,new PersonRowMapper(cityRepository,genderRepository));
    }

    @Override
    public Person getEntity(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource("idPerson", id);

        List<Person> persons = namedParameterJdbcTemplate.query("SELECT * FROM Person WHERE IDPerson =:idPerson", parameters,new PersonRowMapper(cityRepository,genderRepository));
        if(!persons.isEmpty()){
            return persons.get(0);
        }
        return null;
    }

    @Override
    public Boolean updateEntity(Person person) {
        int cityId = cityRepository.getCityIdByName(person.getCity());
        System.out.println(cityId);
        try{
            jdbcTemplate.update(
                    "UPDATE Person set FirstName = ?,LastName = ?, HostStatus = ?, IsActive = ?,AboutMe = ?, BirthDate = ?,CityID = ?  where IDPerson = ?",
                    person.getFirstName(),person.getLastName(),person.getIsHosting(),person.getIsActive(),person.getAboutMe(),person.getBirthdate(),cityId,person.getIdPerson());
        }catch (Exception e){
            System.out.println(e);
            return false;}


        return true;
    }

    @Override
    public Person insertEntity(Person person) {
        Map<String, Object> params = new HashMap<>();
        params.put("FirstName", person.getFirstName());
        params.put("LastName", person.getLastName());
        params.put("Email", person.getEmail());
        params.put("HostStatus", person.getIsHosting());
        params.put("IsActive", person.getIsActive());
        params.put("AboutMe", person.getAboutMe());
        params.put("BirthDate", person.getBirthdate());
        params.put("GenderID", genderRepository.getGenderIdByName(person.getGender()));
        params.put("CityID", cityRepository.getCityIdByName(person.getCity()));
        params.put("HashedPassword", person.getHashedPassword());

        int key = inserter.executeAndReturnKey(params).intValue();
        person.setIdPerson(key);

        return person;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }

}
