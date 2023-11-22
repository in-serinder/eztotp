package com.example.eztotp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREAT_DATE="DATE";
    private static final String PWT_="my_pwt";
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STRING = "remarks";


    public database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        String creat_table_query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STRING + " TEXT, " +
                CREAT_DATE    +"TEXT"    +
                PWT_+"TEXT)";
        db.execSQL(creat_table_query);

    }
//update database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long insertString(String remarks, String pwt,String pwt_date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STRING, remarks);
        values.put(PWT_,pwt);
        values.put(CREAT_DATE,pwt_date);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllstrings(){
        SQLiteDatabase db=getReadableDatabase();
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }



    ////r and w




}
