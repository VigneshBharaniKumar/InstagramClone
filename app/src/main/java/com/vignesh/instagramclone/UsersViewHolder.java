package com.vignesh.instagramclone;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    private TextView txtUserName;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);

        txtUserName = itemView.findViewById(R.id.txtUserName_RecyclerView);

    }

    public TextView getTxtUserName() {
        return txtUserName;
    }
}
