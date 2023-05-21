package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.Gender;
import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.repository.CityRepository;
import hr.algebra.hostandtravel.repository.GenderRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    CityRepository cityRepository;
    GenderRepository genderRepository;
    public PersonRowMapper(CityRepository cityRepository,GenderRepository genderRepository){
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
    }
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        boolean hostStatus = rs.getInt("HostStatus") == 1;
        boolean isActive = rs.getInt("IsActive") == 1;
        String city = cityRepository.getCityNameById(rs.getInt("CityID"));
        String gender  = genderRepository.getGenderNameById(rs.getInt("GenderID"));


        return new Person(
                rs.getInt("IDPerson"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                hostStatus,
                isActive,
                rs.getString("AboutMe"),
                rs.getDate("BirthDate").toLocalDate(),
                gender,
                city,
                rs.getString("HashedPassword"));
    }
}

