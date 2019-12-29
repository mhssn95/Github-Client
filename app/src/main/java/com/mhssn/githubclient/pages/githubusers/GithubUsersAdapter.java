package com.mhssn.githubclient.pages.githubusers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhssn.githubclient.R;
import com.mhssn.githubclient.model.User;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersAdapter extends RecyclerView.Adapter<GithubUsersAdapter.GithubUserViewHolder> {

    private ArrayList<User> usersList = new ArrayList<>();
    private OnGithubUserClickedListener listener;

    public GithubUsersAdapter(OnGithubUserClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_user_item, parent, false);
        return new GithubUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder holder, int position) {
        User currentUser = usersList.get(position);
        holder.userName.setText(currentUser.getName());
        holder.userDescription.setText(new StringBuilder()
                .append(currentUser.getBio() == null ? "" : currentUser.getBio())
                .append('\n')
                .append(currentUser.getCompany() == null ? "" : currentUser.getCompany())
        );

        Glide.with(holder.itemView)
                .load(currentUser.getAvatarUrl())
                .into(holder.userImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(currentUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public
    void setUsers(List<User> users) {
        usersList.clear();
        usersList.addAll(users);
        notifyDataSetChanged();
    }

    class GithubUserViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView userDescription;

        public GithubUserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.img_user);
            userName = itemView.findViewById(R.id.tv_username);
            userDescription = itemView.findViewById(R.id.tv_user_desc);
        }
    }

    interface OnGithubUserClickedListener{
        void onClick(User user);
    }
}
