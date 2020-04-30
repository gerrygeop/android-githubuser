package com.example.githubuser;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.githubuser.db.FavoriteHelper;
import com.example.githubuser.model.User;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubuser.adapter.SectionsPagerAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONObject;

import java.util.Objects;

public class DetailUserActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    private static final String API_KEY = BuildConfig.GITHUB_API_KEY;
    private MenuItem menuItem;
    private FavoriteHelper favHelper;
    private User user;
    private ProgressBar progressBar;
    private TextView tvUsername, tvName, tvCompany, tvLocation, tvRepos;
    private ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        tvUsername = findViewById(R.id.tvUsername);
        tvName = findViewById(R.id.tvName);
        tvCompany = findViewById(R.id.tvCompany);
        tvLocation = findViewById(R.id.tvLocation);
        tvRepos = findViewById(R.id.tvRepos);
        imgAvatar = findViewById(R.id.avatar_id);
        progressBar = findViewById(R.id.progressBar2);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.detail_user);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        showDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        menuItem = menu.findItem(R.id.action_favorite);
//        if (favHelper.isExist(user)) setFavorite(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.action_favorite){
            if (!favHelper.isExist(user)) {
                long result = favHelper.insert(user);
                if (result > 0){
                    Toast.makeText(this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
                    setFavorite(true);
//                    updateWidget(this);
                }
            } else {
                int result = favHelper.delete(user.getId());
                if (result > 0){
                    Toast.makeText(this, R.string.delete_favorite, Toast.LENGTH_SHORT).show();
                    setFavorite(false);
//                    updateWidget(this);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDetail() {
        progressBar.setVisibility(View.VISIBLE);
        user = getIntent().getParcelableExtra(EXTRA_USER);
        if (user != null) {
            tvUsername.setText(user.getUsername());
            Glide.with(DetailUserActivity.this)
                    .load(user.getAvatar())
                    .into(imgAvatar);
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/" + user.getUsername();

        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token " + API_KEY);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
                    User userItems = new User();
                    userItems.setName(object.getString("name"));
                    userItems.setCompany(object.getString("company"));
                    userItems.setLocation(object.getString("location"));
                    userItems.setRepository(object.getString("repos_url"));

                    tvName.setText(userItems.getName());
                    tvCompany.setText(userItems.getCompany());
                    tvLocation.setText(userItems.getLocation());
                    tvRepos.setText(userItems.getRepository());


                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage =  statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(DetailUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFavorite(Boolean favorite) {
        if (favorite) {
            menuItem.setIcon(R.drawable.ic_favorite_red_24dp);
        } else {
            menuItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favHelper != null)
            favHelper.close();
    }

//    private static void updateWidget(Context context) {
//        Intent intent = new Intent(context, ImageBannerWidget.class);
//        intent.setAction(ImageBannerWidget.UPDATE_WIDGET);
//        context.sendBroadcast(intent);
//    }
}