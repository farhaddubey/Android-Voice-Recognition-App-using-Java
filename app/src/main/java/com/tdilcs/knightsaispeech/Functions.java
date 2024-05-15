package com.tdilcs.knightsaispeech;

import java.util.Calendar;

public class Functions {
    public static String Wishme(){
        String s="";
        Calendar c= Calendar.getInstance();
        int time=c.get(Calendar.HOUR_OF_DAY);

        if(time>=0 && time <=12){
            s="Good Morning Sir";
        }else if(time>=12 && time <=16){
            s="Good AfterNoon Sir";
        }else if(time>=16 && time <=24){
            s="Good Night Sir";
        }
        return  s;
    }
}
