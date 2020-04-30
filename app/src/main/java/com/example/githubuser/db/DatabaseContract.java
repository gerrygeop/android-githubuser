package com.example.githubuser.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.githubuser";
    private static final String SCHEME = "content";

    public static String TABLE_FAVORITE = "favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String ID = "id";
        public static String LOGIN = "login";
        public static String NAME = "name";
        public static String COMPANY = "company";
        public static String LOCATION = "location";
        public static String REPOS = "repos_url";
        public static String AVATAR = "avatar_url";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
