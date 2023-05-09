package hr.algebra.hostandtravel.domain;

public  enum Gender{
    MALE(assignId("Male")),FEMALE(assignId("Female"));
    public final Integer id;

    private Gender(Integer id) {
        this.id = id;
    }

    private static Integer assignId(String string){ //TODO Currently hardcoded
        if(string.equals("Male")) {return 1;}
        else if (string.equals("Female")) {return 2;}

        return -1;
    }
}