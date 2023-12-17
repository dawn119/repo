package com.by.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public  static  String getYm(){
        LocalDate localDate=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yMM");
        String ym=localDate.format(formatter);
        return ym;
    }

    public static  void main(String [] args)
    {
        System.out.println(getYm());
    }
}
