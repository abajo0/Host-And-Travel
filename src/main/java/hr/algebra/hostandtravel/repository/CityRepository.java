package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.City;
import hr.algebra.hostandtravel.domain.rowmapper.CityRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@EnableAutoConfiguration
public class CityRepository {
    private static final List<City> cityList = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;


    public CityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        if(cityList.isEmpty()){
            cityList.addAll(jdbcTemplate.query("SELECT * FROM City", new CityRowMapper()));
        }
    }

    public int getCityIdByName(String cityName){
        Optional<City> city = cityList.stream().filter(c -> c.getCityName().equalsIgnoreCase(cityName)).findAny();
        if(city.isPresent()){
            return city.get().getIdCity();
        }else{
            return -1;
        }

    }

    public String getCityNameById(int id){
        Optional<City> city = cityList.stream().filter(c -> c.getIdCity() == id).findAny();
        if(city.isPresent()){
            return city.get().getCityName();
        }else{
            return null;
        }
    }


    public List<String> getAllCityNames(){
        return cityList.stream().map(City::getCityName).toList();
    }

    public List<City> getAllCities(){
        return new ArrayList<>(cityList);
    }

    public List<City> getCitiesByCountryId(int id){
        return cityList.stream().filter(c -> c.getCountryId() == id).toList();
    }



}
