package com.mhssn.githubclient.pages.githubusers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.List;

public class GithubUsersActivity extends AppCompatActivity {

    private static final String TAG = "GithubUsersActivity";
    private RecyclerView usersList;
    private GithubUsersAdapter usersAdapter;
    private FloatingActionButton addUserFab;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout listIsEmptyLayout;
    private UserRepository userRepository;

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
        swipeRefresh.setOnRefreshListener(() -> new GetUsersAsyncTask().execute());
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUsersAsyncTask().execute();
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
                    new AddUserAsyncTask().execute(newUsername);
                }).create();
        alert.show();
    }

    private void setUsers(List<GithubUser> githubUsers) {
        if (githubUsers.size() == 0) {
            listIsEmptyLayout.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else {
            listIsEmptyLayout.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
        }
        swipeRefresh.setRefreshing(false);
        usersAdapter.setUsers(githubUsers);
    }

    private class GetUsersAsyncTask extends AsyncTask<Void, Void, List<GithubUser>> {

        @Override
        protected List<GithubUser> doInBackground(Void... voids) {
            return userRepository.getUsers();
        }

        @Override
        protected void onPostExecute(List<GithubUser> githubUsers) {
            setUsers(githubUsers);
        }
    }

    private class AddUserAsyncTask extends AsyncTask<String, Void, Pair<List<GithubUser>, Boolean>> {
        @Override
        protected Pair<List<GithubUser>, Boolean> doInBackground(String... strings) {
            boolean added = userRepository.addUser(strings[0]);
            return new Pair<>(userRepository.getUsers(), added);
        }

        @Override
        protected void onPostExecute(Pair<List<GithubUser>, Boolean> pair) {
            if (!pair.second) {
                Toast.makeText(GithubUsersActivity.this, R.string.failed_to_add_user, Toast.LENGTH_SHORT).show();
            }
            setUsers(pair.first);
        }
    }
}
