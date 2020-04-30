package com.example.githubuser;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.githubuser.ui.favorite.FavoriteFragment;
import com.example.githubuser.ui.home.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        setFragment(new UserFragment());
        navView.setOnNavigationItemSelectedListener(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.localization_settings:
                Intent language = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(language);
                break;

            case R.id.reminder_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_home);
                fragment = new UserFragment();
                break;
            case R.id.navigation_favorite:
                Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_favorite);
                fragment = new FavoriteFragment();
                break;
        }
        setFragment(fragment);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
    }
}
