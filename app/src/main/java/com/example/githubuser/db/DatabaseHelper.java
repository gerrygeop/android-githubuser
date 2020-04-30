package com.example.githubuser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.AVATAR;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.COMPANY;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.ID;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOCATION;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOGIN;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.REPOS;
import static com.example.githubuser.db.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbfavoriteuser";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_FAVORITE = String.format("CREATE TABLE %s " +
            " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NULL," +
            " %s TEXT NULL," +
            " %s TEXT NULL," +
            " %s TEXT NULL," +
            " %s TEXT NULL," +
            " %s TEXT NULL)",
            TABLE_FAVORITE,
            ID,
            LOGIN,
            NAME,
            COMPANY,
            LOCATION,
            REPOS,
            AVATAR);

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
