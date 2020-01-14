package com.example.retrofittest1.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.retrofittest1.model.Publish;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "publicationDb";
    public static final String TABLE_PUBLICATION = "publications";

    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TIME_CREATE = "valueOfcreate";
    public static final String KEY_NUM_COMMENTS = "Num_comments";
    public static final String KEY_THUMBNAIL = "thumbnail";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {


        dataBase.execSQL("create table " + TABLE_PUBLICATION + "(" + KEY_TIME_CREATE
                + " integer primary key," + KEY_AUTHOR + " text," + KEY_NUM_COMMENTS + " text,"
                + KEY_THUMBNAIL + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        dataBase.execSQL("drop table if exists " + TABLE_PUBLICATION);

        onCreate(dataBase);
    }

    public void insert(List<Publish> publishes) {


        ContentValues contentValues = new ContentValues();

        for (Publish p : publishes) {


            contentValues.put(DBHelper.KEY_AUTHOR, p.getAuthor());
            contentValues.put(DBHelper.KEY_TIME_CREATE, p.getCreated());
            contentValues.put(DBHelper.KEY_NUM_COMMENTS, p.getNum_comments());
            contentValues.put(DBHelper.KEY_THUMBNAIL, p.getThumbnail());//хранится url закачки из инета, а не в телефоне


            getWritableDatabase().insert(DBHelper.TABLE_PUBLICATION, null, contentValues);
        }


    }

    public List<Publish> read() {

        List<Publish> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(DBHelper.TABLE_PUBLICATION, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int authorIndex = cursor.getColumnIndex(DBHelper.KEY_AUTHOR);
            int createIndex = cursor.getColumnIndex(DBHelper.KEY_TIME_CREATE);
            int Num_commentsIndex = cursor.getColumnIndex(DBHelper.KEY_NUM_COMMENTS);
            int pictureIndex = cursor.getColumnIndex(DBHelper.KEY_THUMBNAIL);
            do {

                Publish publish = new Publish(cursor.getString(authorIndex),cursor.getLong(createIndex),
                        cursor.getInt(Num_commentsIndex),cursor.getString(pictureIndex));
                list.add(publish);
                Log.d("mLog",
                        ", authorname = " + cursor.getString(authorIndex));

            } while (cursor.moveToNext());
        }

        cursor.close();


       return list;
    }


}
