package com.mhssn.githubclient.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.mhssn.githubclient.model.DataCallBack;
import com.mhssn.githubclient.model.GithubRepository;
import com.mhssn.githubclient.model.GithubUser;
import com.mhssn.githubclient.repository.database.UserDataRepository;
import com.mhssn.githubclient.repository.remote.UserRemoteRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static UserRepository instance;
    private final Handler handler;
    private UserRemoteRepository remote;
    private UserDataRepository data;

    private UserRepository(Context context) {
        handler = new Handler(Looper.getMainLooper());
        remote = UserRemoteRepository.getInstance();
        data = new UserDataRepository(context);
    }

    public synchronized static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void getUsers(String sort, DataCallBack<List<GithubUser>> callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<GithubUser> users = data.getAllUsers(sort);
            handler.post(() -> callback.onSuccess(users));
        });
    }

    public void addUser(String username, DataCallBack<Void> callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        remote.getUser(username).enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                executor.execute(() -> {
                    if (response.isSuccessful()) {
                        data.insertUser(response.body());
                        handler.post(() -> callback.onSuccess(null));
                    } else {
                        handler.post(() -> callback.onError(response.message()));
                    }
                });
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });
    }

    public void getRepositories(String username, DataCallBack<List<GithubRepository>> dataCallBack) {
        remote.getRepositories(username).enqueue(new Callback<List<GithubRepository>>() {
            @Override
            public void onResponse(Call<List<GithubRepository>> call, Response<List<GithubRepository>> response) {
                dataCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<GithubRepository>> call, Throwable t) {
                dataCallBack.onError(t.getLocalizedMessage());
            }
        });
    }
}
