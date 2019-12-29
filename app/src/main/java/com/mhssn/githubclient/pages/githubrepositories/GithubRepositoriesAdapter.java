package com.mhssn.githubclient.pages.githubrepositories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhssn.githubclient.R;
import com.mhssn.githubclient.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class GithubRepositoriesAdapter extends RecyclerView.Adapter<GithubRepositoriesAdapter.RepositoriesViewHolder> {

    private ArrayList<Repository> repositoriesList = new ArrayList<>();
    private OnGithubRepositoryClickedListener listener;

    public GithubRepositoriesAdapter(OnGithubRepositoryClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RepositoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_repository_item, parent, false);
        return new RepositoriesViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesViewHolder holder, int position) {
        Repository currentRepo = repositoriesList.get(position);
        holder.name.setText(currentRepo.getName());
        holder.description.setText(currentRepo.getDescription());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(currentRepo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repositoriesList.size();
    }

    public void setRepositories(List<Repository> repositories) {
        repositoriesList.clear();
        repositoriesList.addAll(repositories);
        notifyDataSetChanged();
    }


    class RepositoriesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        public RepositoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            description = itemView.findViewById(R.id.tv_desc);
        }
    }

    interface OnGithubRepositoryClickedListener{
        void onClick(Repository repository);
    }
}
