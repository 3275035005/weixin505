package com.feedback.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }
}
