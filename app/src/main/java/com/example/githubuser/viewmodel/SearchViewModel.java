package com.example.githubuser.viewmodel;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<User>> listUser = new MutableLiveData<>();
    private static final String API_KEY = BuildConfig.GITHUB_API_KEY;

    public void setSearchUser(String query) {
        final ArrayList<User> listItem = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/search/users?q=" + query;

        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token " + API_KEY);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("items");

                    for (int i=0; i < list.length(); i++) {
                        JSONObject user = list.getJSONObject(i);
                        User userItems = new User();
                        userItems.setId(user.getInt("id"));
                        userItems.setUsername(user.getString("login"));
                        userItems.setAvatar(user.getString("avatar_url"));
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

    public LiveData<ArrayList<User>> getUser() {
        return listUser;
    }
}