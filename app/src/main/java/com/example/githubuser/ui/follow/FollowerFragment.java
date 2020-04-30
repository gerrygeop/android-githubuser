package com.example.githubuser.ui.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuser.DetailUserActivity;
import com.example.githubuser.R;
import com.example.githubuser.adapter.UserAdapter;
import com.example.githubuser.model.User;
import com.example.githubuser.viewmodel.UserViewModel;

import java.util.ArrayList;

public class FollowerFragment extends Fragment {
/*
    private RecyclerView rvGithub;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private User user;
 */

    public FollowerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progressBar);
        rvGithub = view.findViewById(R.id.rv_user);
        rvGithub.setHasFixedSize(true);
    }

    private void showRecyclerUser(String username) {
        rvGithub.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter();
        userAdapter.notifyDataSetChanged();
        rvGithub.setAdapter(userAdapter);
        username = user.getUsername();

        UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.setFollower(username = user.getUsername());

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null) {
                    userAdapter.setData(users);
                    showLoading(false);
                }
            }
        });

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

     */
}