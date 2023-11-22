package com.example.eztotp;

import java.text.SimpleDateFormat;
import java.util.Date;
import  java.time.Instant;

public class unix_time {

    public static String unix_time_str() {
        long curmin=System.currentTimeMillis();
        long uniixtime=curmin/1000;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(curmin);
        String formaydate=simpleDateFormat.format(date);
        return "Unix time:"+uniixtime+"\n格式化Unix时间:"+formaydate;

    }
}
