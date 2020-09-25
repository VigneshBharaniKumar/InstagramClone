package com.vignesh.instagramclone;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersPostViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgPicture;
    private TextView txtCaption;

    public UsersPostViewHolder(@NonNull View itemView) {
        super(itemView);

        imgPicture = itemView.findViewById(R.id.imgPicture_UsersPost);
        txtCaption = itemView.findViewById(R.id.txtCaption_UsersPost);

    }

    public ImageView getImgPicture() {
        return imgPicture;
    }

    public TextView getTxtCaption() {
        return txtCaption;
    }
}
