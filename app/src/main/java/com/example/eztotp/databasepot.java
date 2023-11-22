package com.example.eztotp;

import android.content.Context;
import android.database.Cursor;

public class databasepot {

    public static void writedatabase(Context context,String remarks,String pwt,String pwddate){
        database dbhelp=new database(context);
        long newrowid=dbhelp.insertString(remarks,pwt,pwddate);
    }


    public static String readdatabase(Context context,String remarks,String pwt,String pwddate){
        String info="";
        database database=new database(context);
        Cursor cursor=database.getAllstrings();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String remarksf=context.getString(cursor.getColumnIndex(remarks));
                String pwtf=context.getString(cursor.getColumnIndex(pwt));
                String pwddatef=context.getString(cursor.getColumnIndex(pwddate));
                cursor.moveToNext();
                info=remarksf+"\n"+pwtf+"\n"+pwddatef;
            }
        }
        cursor.close();
        return info;
    }
}

//String remarksf=context.getString(cursor.getColumnIndex("remaks"));
//                String pwtf=context.getString(cursor.getColumnIndex("my_pwt"));
//                String pwddatef=context.getString(cursor.getColumnIndex("DATE"));