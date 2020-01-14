package com.example.retrofittest1.managers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager {

    public static String millsToDate(long mmls){


       long difT = System.currentTimeMillis()/1000;
        Date date = new Date(difT);
        Date datemils= new Date(mmls);

       long d = date.getTime();
       long difTime = difT - mmls;
        difTime = difTime/3600;
        String pattern = "yyyy.MM.dd G 'at' HH:mm:ss z";
      //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String s = simpleDateFormat.format(date);
//        String s1 = simpleDateFormat.format(dateNow);
        return difTime+" hours ago";
    }
}
