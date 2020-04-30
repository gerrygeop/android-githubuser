package com.example.githubuser.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuser.DetailUserActivity;
import com.example.githubuser.R;
import com.example.githubuser.adapter.UserAdapter;
import com.example.githubuser.model.User;
import com.example.githubuser.viewmodel.SearchViewModel;
import com.example.githubuser.viewmodel.UserViewModel;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private RecyclerView rvGithub;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private TextView tvPage;

    public UserFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progressBar);
        rvGithub = view.findViewById(R.id.rv_user);
        rvGithub.setHasFixedSize(true);
        tvPage = view.findViewById(R.id.tvHomepage);

        showLoading(true);
        showRecyclerUser();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();

        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    showLoading(true);
                    tvPage.setVisibility(View.GONE);
                    showSearchUser(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setRvGithub() {
        rvGithub.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter();
        userAdapter.notifyDataSetChanged();
        rvGithub.setAdapter(userAdapter);
    }

    private void showSearchUser(String query) {
        setRvGithub();
        SearchViewModel searchViewModel = new ViewModelProvider(UserFragment.this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        searchViewModel.setSearchUser(query);
        searchViewModel.getUser().observe(getViewLifecycleOwner(), getUser);
        setClickItem();
    }

    private void showRecyclerUser() {
        setRvGithub();
        UserViewModel userViewModel = new ViewModelProvider(UserFragment.this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.setAllUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), getUser);
        setClickItem();
    }

    private final Observer<ArrayList<User>> getUser = new Observer<ArrayList<User>>() {
        @Override
        public void onChanged(ArrayList<User> users) {
            if (users != null) {
                userAdapter.setData(users);
                tvPage.setVisibility(View.GONE);
                showLoading(false);
            }
        }
    };

    private void setClickItem() {
        userAdapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(User user) {
                Intent intent = new Intent(getContext(), DetailUserActivity.class);
                intent.putExtra(DetailUserActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
