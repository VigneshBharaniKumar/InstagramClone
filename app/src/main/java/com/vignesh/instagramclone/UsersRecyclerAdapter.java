package com.vignesh.instagramclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private ArrayList usersList;
    private OnClickUserInterface onClickUserInterface;

    public interface OnClickUserInterface {
        void onClickUser (String selectedUser);
        void onLongClickUser (String selectedUser);
    }

    public UsersRecyclerAdapter(ArrayList usersList, OnClickUserInterface onClickUserInterface) {
        this.usersList = usersList;
        this.onClickUserInterface = onClickUserInterface;
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
    public void onBindViewHolder(@NonNull UsersViewHolder holder, final int position) {

        holder.getTxtUserName().setText(usersList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUserInterface.onClickUser(usersList.get(position).toString());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickUserInterface.onLongClickUser(usersList.get(position).toString());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


}
