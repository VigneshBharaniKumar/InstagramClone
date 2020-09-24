package com.vignesh.instagramclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private ArrayList usersList;

    public UsersRecyclerAdapter(ArrayList usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.users_view_holder, parent, false);
        UsersViewHolder viewHolder = new UsersViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        holder.getTxtUserName().setText(usersList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
