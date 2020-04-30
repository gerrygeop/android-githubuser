package com.example.githubuser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.githubuser.model.User;

import java.util.ArrayList;

import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.AVATAR;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.COMPANY;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.ID;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOCATION;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOGIN;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.REPOS;
import static com.example.githubuser.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> arrayList = new ArrayList<>();
        User users;

        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                NAME + " ASC",
                null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                users = new User(cursor);
                users.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                users.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(LOGIN)));
                users.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                users.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COMPANY)));
                users.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(LOCATION)));
                users.setRepository(cursor.getString(cursor.getColumnIndexOrThrow(REPOS)));
                users.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR)));

                arrayList.add(users);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean isExist(User user) {
        database = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + ID + "=" + user.getId();

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insert(User user) {
        ContentValues args = new ContentValues();
        args.put(ID, user.getId());
        args.put(LOGIN, user.getUsername());
        args.put(NAME, user.getName());
        args.put(COMPANY, user.getCompany());
        args.put(LOCATION, user.getLocation());
        args.put(REPOS, user.getRepository());
        args.put(AVATAR, user.getRepository());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int delete(int id) {
        return database.delete(DATABASE_TABLE, ID + " = '" + id + "'", null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID + " DESC");
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE,
                null,
                ID + " =?",
                new String[]{id},
                null,
                null,
                null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " =?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID + " =?", new String[]{id});
    }
}
