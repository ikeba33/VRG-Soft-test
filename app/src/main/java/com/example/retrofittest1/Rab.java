package com.example.retrofittest1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rab {

    public static String millsToDate(long mmls){
        Date date = new Date();
        date.setTime(mmls);
        Date dateNow = new Date();

       long difTime = dateNow.getTime() - date.getTime();
        difTime = difTime/1000/60/60;
        String pattern = "yyyy.MM.dd G 'at' HH:mm:ss z";
      //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String s = simpleDateFormat.format(date);
//        String s1 = simpleDateFormat.format(dateNow);
        return difTime+" hours ago";
    }
}
