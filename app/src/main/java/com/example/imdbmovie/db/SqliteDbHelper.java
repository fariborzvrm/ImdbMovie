package com.example.imdbmovie.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by A.mehdizadeh on 1/1/2020.
 * mehdizadeh7250@gmail.com
 */
public class SqliteDbHelper extends SQLiteOpenHelper {
    static final String TABLE_NAME = "MovieDetails";

    public SqliteDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY, " +
                "movie_name TEXT" +
                ")";
        db.execSQL(query);
    }

    public void inserIntoTable(int ID, String movie_name) {
        String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (_id,movie_name) VALUES ( "
                + ID + ","
                + "'" + movie_name + "'"
                + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT_QUERY);
        db.close();
    }

    public void deleteFromTable(int ID) {
        String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE _id = " + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_QUERY);
        db.close();
    }

    public boolean getMovieDetail(int ID) {
        String MOVIE_QUERY = "SELECT _id FROM " + TABLE_NAME +
                " WHERE _id = " + ID;
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(MOVIE_QUERY, null);
        while (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
