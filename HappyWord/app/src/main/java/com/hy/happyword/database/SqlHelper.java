package com.hy.happyword.database;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by huyu on 2017/4/7.
 */
public class SqlHelper {

    public static String BOOKLIST_TABLE = "BOOKS";
    public static String WORDLIST_TABLE = "PLAN";
    public static String ATTENTION_TABLE = "ATTENTION";
    public  static final String DB_NAME = "data/data/wordroid.model/databases/wordroid.db";

    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_NAME,null);

    public void Insert(Context context, String table, ContentValues values){
        try{
            db.insert(table,null,values);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public void CreateTable(Context context,String table){
        String sql="CREATE TABLE " + table + " ( ID text not null, SPELLING text not null , MEANNING text not null, PHONETIC_ALPHABET text, LIST text not null" + ");";
        try{
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public void Update(Context context,String table,ContentValues values,String whereClause,String[] whereArgs){
        try{
            db.update(table,values,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public Cursor Query(Context context,String table,String[] columns,String selection,String[] selectionArgs,
                        String groupBy,String having,String orderBy){
        Cursor cursor = null;
        try{
            cursor = db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
        return cursor;
    }

    public void Delete(Context context,String table,String whereClause,String[] whereArgs){
        try{
            db.delete(table,whereClause,whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    public void DeleteTable(Context context,String table){
        String sql = "drop table "+ table;
        try{
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

}