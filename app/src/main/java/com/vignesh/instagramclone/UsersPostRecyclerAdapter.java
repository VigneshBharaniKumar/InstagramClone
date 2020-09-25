package com.vignesh.instagramclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

public class UsersPostRecyclerAdapter extends RecyclerView.Adapter<UsersPostViewHolder> {

    private List<ParseObject> usersPostObjects;

    private static final String PHOTO_CLASS_KEY = "Photo";
    private static final String PICTURE_KEY = "picture";
    private static final String CAPTION_KEY = "caption";
    private static final String USER_NAME_KEY = "username";
    private static final String CREATED_BY_KEY = "createdAt";


    public UsersPostRecyclerAdapter(List<ParseObject> usersPostObjects) {
        this.usersPostObjects = usersPostObjects;
    }

    @NonNull
    @Override
    public UsersPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.users_post_view_holder, parent, false);
        UsersPostViewHolder viewHolder = new UsersPostViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersPostViewHolder holder, int position) {


        holder.getTxtCaption().setText(usersPostObjects.get(position).get(CAPTION_KEY).toString());

        ParseFile postPicture = (ParseFile) usersPostObjects.get(position).get(PICTURE_KEY);
        postPicture.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null) {

                    if (data != null) {

                        Bitmap imgBitMap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        holder.getImgPicture().setImageBitmap(imgBitMap);


                    } else {


                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersPostObjects.size();
    }
}
