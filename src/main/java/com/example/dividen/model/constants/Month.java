package com.example.dividen.model.constants;

public enum Month {

    JAN("Jan", 1),
    FEB("Feb", 2),
    MAR("Mar", 3),
    APR("Apr", 4),
    MAY("May", 5),
    JUN("Jun", 6),
    JUL("Jul", 7),
    AUG("Aug", 8),
    SEP("Sep", 9),
    OCT("Oct", 10),
    NOV("Nov", 11),
    DEC("Dec", 12);

    Month(String s, int number) {
        this.s = s;
        this.number = number;
    }

    private String s;
    private int number;

    public static int stringToNumber(String s) {
        for (var month : Month.values()) {
            if (month.s.equals(s)) {
                return month.number;
            }
        }
        return -1;
    }
}
