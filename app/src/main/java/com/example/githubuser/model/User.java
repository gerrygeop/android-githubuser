package com.example.githubuser.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.AVATAR;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.COMPANY;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.ID;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOCATION;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.LOGIN;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.githubuser.db.DatabaseContract.FavoriteColumns.REPOS;
import static com.example.githubuser.db.DatabaseContract.getColumnInt;
import static com.example.githubuser.db.DatabaseContract.getColumnString;

public class User implements Parcelable {
    private int id;
    private String username;
    private String name;
    private String avatar;
    private String company;
    private String location;
    private String repository;
    private String url;
    private String follower_url;
    private String following_url;

    public User() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFollower_url() {
        return follower_url;
    }

    public void setFollower_url(String follower_url) {
        this.follower_url = follower_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        name = in.readString();
        avatar = in.readString();
        company = in.readString();
        location = in.readString();
        repository = in.readString();
        url = in.readString();
        follower_url = in.readString();
        following_url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(repository);
        dest.writeString(url);
        dest.writeString(follower_url);
        dest.writeString(following_url);
    }

    public User(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.username = getColumnString(cursor, LOGIN);
        this.name = getColumnString(cursor, NAME);
        this.company = getColumnString(cursor, COMPANY);
        this.location = getColumnString(cursor, LOCATION);
        this.repository = getColumnString(cursor, REPOS);
        this.avatar = getColumnString(cursor, AVATAR);
    }
}
