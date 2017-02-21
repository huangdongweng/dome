package com.example.administrator.phonesefe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/29.
 */

public class TimeUtils {
    public static String fomartTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        String data =format.format(new Date(time));
        return data;
    }
}
