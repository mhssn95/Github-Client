package com.mhssn.githubclient.pages.githubrepositories;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhssn.githubclient.R;
import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;
import com.mhssn.githubclient.repository.UserRepository;
import com.mhssn.githubclient.model.DataCallBack;

import java.util.List;

public class GithubRepositoriesActivity extends AppCompatActivity {

    private GithubUser currentGithubUser;
    private ImageView userImage;
    private TextView userName;
    private TextView userDescription;
    private RecyclerView repositoriesList;
    private LinearLayout listIsEmptyLayout;
    private ProgressBar loading;
    private GithubRepositoriesAdapter repositoriesAdapter;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_repositories);

        userRepository = UserRepository.getInstance(this);
        userImage = findViewById(R.id.img_user);
        userName = findViewById(R.id.tv_username);
        userDescription = findViewById(R.id.tv_user_desc);
        repositoriesList = findViewById(R.id.rv_repositories);
        listIsEmptyLayout = findViewById(R.id.ll_list_is_empty);
        loading = findViewById(R.id.loading);

        initRecyclerView();
        loadUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userRepository.getRepositories(currentGithubUser.getUsername(), new DataCallBack<List<GithubRepository>>() {
            @Override
            public void onSuccess(List<GithubRepository> data) {
                setRepositories(data);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(GithubRepositoriesActivity.this, message, Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
            }
        });
    }

    private void loadUser() {
        currentGithubUser = getIntent().getParcelableExtra("user");

        userName.setText(currentGithubUser.getName());
        userDescription.setText(new StringBuilder()
                .append(currentGithubUser.getBio() == null ? "" : currentGithubUser.getBio())
                .append('\n')
                .append(currentGithubUser.getCompany() == null ? "" : currentGithubUser.getCompany())
        );

        Glide.with(this)
                .load(currentGithubUser.getAvatarUrl())
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

    private void setRepositories(List<GithubRepository> repositories) {
        if (repositories.size() == 0) {
            listIsEmptyLayout.setVisibility(View.VISIBLE);
            repositoriesList.setVisibility(View.GONE);
        } else {
            repositoriesList.setVisibility(View.VISIBLE);
            listIsEmptyLayout.setVisibility(View.GONE);
        }
        loading.setVisibility(View.GONE);
        repositoriesAdapter.setRepositories(repositories);
    }

}
