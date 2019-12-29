package com.mhssn.githubclient.pages.githubrepositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mhssn.githubclient.R;
import com.mhssn.githubclient.model.Repository;
import com.mhssn.githubclient.model.User;
import com.mhssn.githubclient.model.resource.Response;
import com.mhssn.githubclient.repository.userrepository.UserRepository;
import com.mhssn.githubclient.repository.userrepository.UserRepositoryImpl;

import java.util.List;

public class GithubRepositoriesActivity extends AppCompatActivity {

    private User currentUser;
    private ImageView userImage;
    private TextView userName;
    private TextView userDescription;
    private RecyclerView repositoriesList;
    private ProgressBar loading;
    private GithubRepositoriesAdapter repositoriesAdapter;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_repositories);

        userRepository = UserRepositoryImpl.getInstance(this);
        userImage = findViewById(R.id.img_user);
        userName = findViewById(R.id.tv_username);
        userDescription = findViewById(R.id.tv_user_desc);
        repositoriesList = findViewById(R.id.rv_repositories);
        loading = findViewById(R.id.loading);

        initRecyclerView();
        loadUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUserRepositories().execute(currentUser.getUsername());
    }

    private void loadUser() {
        currentUser = getIntent().getParcelableExtra("user");

        userName.setText(currentUser.getName());
        userDescription.setText(new StringBuilder()
                .append(currentUser.getBio() == null ? "" : currentUser.getBio())
                .append('\n')
                .append(currentUser.getCompany() == null ? "" : currentUser.getCompany())
        );

        Glide.with(this)
                .load(currentUser.getAvatarUrl())
                .into(userImage);

    }

    private void initRecyclerView() {
        repositoriesList.setLayoutManager(new LinearLayoutManager(this));
        repositoriesList.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        repositoriesAdapter = new GithubRepositoriesAdapter(repository -> {
            openInBrowser(repository.getLink());
        });
        repositoriesList.setAdapter(repositoriesAdapter);
    }

    private void openInBrowser(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private class GetUserRepositories extends AsyncTask<String, Void, Response<List<Repository>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
            repositoriesList.setVisibility(View.GONE);
        }

        @Override
        protected Response<List<Repository>> doInBackground(String... username) {
            return userRepository.getRepositories(username[0]);
        }

        @Override
        protected void onPostExecute(Response<List<Repository>> response) {
            switch (response.getState()) {
                case SUCCESS:
                    repositoriesAdapter.setRepositories(response.getData());
                    break;
                case FAILED:
                    Toast.makeText(GithubRepositoriesActivity.this, R.string.failed_to_get_user_repositories, Toast.LENGTH_SHORT).show();
                    break;
            }
            loading.setVisibility(View.GONE);
            repositoriesList.setVisibility(View.VISIBLE);
        }
    }

}
