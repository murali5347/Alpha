package com.example.murali.alpha.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by murali on 12/8/2016.
 */
public class AlphaData {

    public final String posterName;
    public final String posterText;
    public final String url;
    public final String date;

    public AlphaData(String date, String posterName, String posterText, String url) {
        this.posterName = posterName;
        this.posterText = posterText;
        this.url = url;
        this.date = currentTimeStampToday(date);

    }

    /*
       create the date format
     */
    private static String currentTimeStampToday(String timeStamp) {

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(timeStamp);
            formattedDate = new SimpleDateFormat("EEE, d MMM yyyy 'At' HH:mm:ss z",Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;

    }
}

