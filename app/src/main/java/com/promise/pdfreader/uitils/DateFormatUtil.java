package com.promise.pdfreader.uitils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: liulei
 * @date:2018/3/16
 */
public class DateFormatUtil {

    public static final String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String formateDate(Date date,String formate){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
        return simpleDateFormat.format(date);
    }

    public static  String formateDate(Date date){
        return  formateDate(date,YY_MM_DD_HH_MM_SS);
    }
}
