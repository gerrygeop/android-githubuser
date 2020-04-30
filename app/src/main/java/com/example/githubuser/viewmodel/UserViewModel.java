package com.example.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuser.BuildConfig;
import com.example.githubuser.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class UserViewModel extends ViewModel {

    private MutableLiveData<ArrayList<User>> listUser = new MutableLiveData<>();
    private static final String API_KEY = BuildConfig.GITHUB_API_KEY;

    public void setUser(final String url) {
        final ArrayList<User> listItem = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();

        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token " + API_KEY);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONArray list = new JSONArray(result);

                    for (int i=0; i < list.length(); i++) {
                        JSONObject user = list.getJSONObject(i);
                        User userItems = new User();
                        userItems.setId(user.getInt("id"));
                        userItems.setUsername(user.getString("login"));
                        userItems.setAvatar(user.getString("avatar_url"));
                        userItems.setUrl(user.getString("url"));
                        listItem.add(userItems);
                    }
                    listUser.postValue(listItem);

                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public void setAllUser() {
        String url = "https://api.github.com/users?per_page=10&page=1";
        setUser(url);
    }

    public void setFollowing(String username) {
        String url = "https://api.github.com/users/" + username + "/following";
        setUser(url);
    }

    public void setFollower(String username) {
        String url = "https://api.github.com/users/" + username + "/following";
        setUser(url);
    }

    public LiveData<ArrayList<User>> getUser() {
        return listUser;
    }
}