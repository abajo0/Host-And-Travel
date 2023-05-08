package hr.algebra.hostandtravel.util;


import hr.algebra.hostandtravel.domain.Gender;


public class DBUtil {

    //TODO  Add GenderRepository and delete this class
    private  DBUtil(){}

    public static Gender getGenderById(int id){
        return Gender.MALE;
    }
    public static int getIdByGender(Gender gender){
        return 1;
    }


}
