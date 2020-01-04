package com.mhssn.githubclient.pages.githubusers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhssn.githubclient.R;
import com.mhssn.githubclient.model.GithubUser;
import com.mhssn.githubclient.pages.githubrepositories.GithubRepositoriesActivity;
import com.mhssn.githubclient.repository.UserRepository;
import com.mhssn.githubclient.model.DataCallBack;

import java.util.List;

public class GithubUsersActivity extends AppCompatActivity {

    private static final String TAG = "GithubUsersActivity";
    private RecyclerView usersList;
    private GithubUsersAdapter usersAdapter;
    private FloatingActionButton addUserFab;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout listIsEmptyLayout;
    private UserRepository userRepository;
    private String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_users);

        userRepository = UserRepository.getInstance(this);
        usersList = findViewById(R.id.rv_users);
        addUserFab = findViewById(R.id.fab_add_user);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        listIsEmptyLayout = findViewById(R.id.ll_list_is_empty);
        addUserFab.setOnClickListener(v -> showAddGithubUserDialog());
        swipeRefresh.setOnRefreshListener(() -> fetchUsers());
        initRecyclerView();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        sort = preferences.getString("SORT", "DESC");
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUsers();
    }

    private void initRecyclerView() {
        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new GithubUsersAdapter(user -> {
            Intent intent = new Intent(this, GithubRepositoriesActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        usersList.setAdapter(usersAdapter);
    }

    private void showAddGithubUserDialog() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setView(R.layout.add_github_user_dialog)
                .setPositiveButton(R.string.add, (dialog, which) -> {
                    //Add github user
                    EditText newUsernameEditText = ((AlertDialog) dialog).findViewById(R.id.et_github_user_name);
                    String newUsername = newUsernameEditText.getText().toString();
                    userRepository.addUser(newUsername, new DataCallBack<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            fetchUsers();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(GithubUsersActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }).create();
        alert.show();
    }

    private void showSortDialog() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String[] sortTypes = new String[]{"ASC", "DESC"};
        AlertDialog alert = new AlertDialog.Builder(this)
                .setItems(sortTypes, (dialog, which) -> {
                    String sortType = sortTypes[which];
                    editor.putString("SORT", sortType);
                    editor.apply();
                    sort = sortType;
                    fetchUsers();
                }).create();
        alert.show();
    }

    private void setUsers(List<GithubUser> githubUsers) {
        if (githubUsers.size() == 0) {
            listIsEmptyLayout.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            listIsEmptyLayout.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
        }
        swipeRefresh.setRefreshing(false);
        usersAdapter.setUsers(githubUsers);
    }

    private void fetchUsers() {
        userRepository.getUsers(sort, new DataCallBack<List<GithubUser>>() {
            @Override
            public void onSuccess(List<GithubUser> data) {
                setUsers(data);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(GithubUsersActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_github_users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.user_sort:
                showSortDialog();
                return true;
        }
        return false;
    }

}
