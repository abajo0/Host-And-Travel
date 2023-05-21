package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.City;
import hr.algebra.hostandtravel.domain.Gender;
import hr.algebra.hostandtravel.domain.rowmapper.GenderRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@EnableAutoConfiguration
public class GenderRepository {
    private static final List<Gender> genderList = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;


    public GenderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        if(genderList.isEmpty()){
            genderList.addAll(jdbcTemplate.query("SELECT * FROM Gender", new GenderRowMapper()));
        }
    }

    public int getGenderIdByName(String genderName){
        Optional<Gender> gender = genderList.stream().filter(g -> g.getGenderName().equalsIgnoreCase(genderName)).findAny();
        if(gender.isPresent()){
            return gender.get().getIdGender();
        }else{
            return -1;
        }

    }

    public String getGenderNameById(int id){
        Optional<Gender> gender = genderList.stream().filter(c -> c.getIdGender() == id).findAny();
        if(gender.isPresent()){
            return gender.get().getGenderName();
        }else{
            return null;
        }
    }


    public List<String> getAllGenderNames(){
        return genderList.stream().map(Gender::getGenderName).toList();
    }

    public List<Gender> getAllGenders(){
        return new ArrayList<>(genderList);
    }

}
