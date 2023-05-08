package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.City;
import hr.algebra.hostandtravel.domain.Country;
import hr.algebra.hostandtravel.domain.rowmapper.CityRowMapper;
import hr.algebra.hostandtravel.domain.rowmapper.CountryRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@EnableAutoConfiguration
public class CountryRepository {
    private static final List<Country> countryList = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;

    public CountryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        if(countryList.isEmpty()){
            countryList.addAll(jdbcTemplate.query("SELECT * FROM Country", new CountryRowMapper()));
        }
    }

    public int getCountryIdByName(String countryName){
        Optional<Country> country = countryList.stream().filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny();
        if(country.isPresent()){
            return country.get().getIdCity();
        }else{
            return -1;
        }
    }

    public List<Country> getAllCountries(){
        return new ArrayList<>(countryList);
    }

    public List<String> getAllCountryNames(){
        return countryList.stream().map(Country::getCountryName).toList();
    }


}
