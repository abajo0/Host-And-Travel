package hr.algebra.hostandtravel.util;


import hr.algebra.hostandtravel.domain.Person.Gender;

public class DBUtil {

    private  DBUtil(){}

    public static String getCityById(int id){
        return "Zagreb (Hardcoded value)";
    }
    public static String getCountryByCity(String cityName) {
        return "Croatia (Hardcoded value)";
    }
    public static Gender getGenderById(int id){
        return Gender.MALE;
    }
    public static int getIdByGender(Gender gender){
        throw new RuntimeException("not implementd");
    }

}
