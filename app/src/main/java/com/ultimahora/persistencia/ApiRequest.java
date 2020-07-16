package com.ultimahora.persistencia;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApiRequest {
    public static String apy_key = "9a6d2f3e96ec4e9c9440f436fcae59cb";
    public static String api_link = "http://newsapi.org/v2/";
    public static String api_local = "br";

    public static String requestUrl(String url){
        StringBuilder sb = new StringBuilder(api_link);
        sb.append(String.format("top-headlines?country=%s&apiKey=%s",api_local, apy_key));
        return sb.toString();
    }

    @NotNull
    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMMM yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }
}
