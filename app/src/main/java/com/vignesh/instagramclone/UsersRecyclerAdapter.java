package com.vignesh.instagramclone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private ArrayList usersList;
    private OnClickUserInterface onClickUserInterface;

    public interface OnClickUserInterface {
        void onClickUser (String selectedUser);
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
                /*Intent intent = new Intent(v.getContext(), UsersPostActivity.class);
                v.getContext().startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


}
