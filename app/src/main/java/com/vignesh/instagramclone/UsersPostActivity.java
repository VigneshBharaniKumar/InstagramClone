package com.vignesh.instagramclone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UsersPostActivity extends AppCompatActivity {

    private static final String PHOTO_CLASS_KEY = "Photo";
    private static final String PICTURE_KEY = "picture";
    private static final String CAPTION_KEY = "caption";
    private static final String USER_NAME_KEY = "username";
    private static final String CREATED_BY_KEY = "createdAt";

    private String selectedUserName;

    private MaterialToolbar toolbar;

    private SweetAlertDialog alertDialog;

    //private LinearLayout linearLayout;

    private RecyclerView usersPostRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        selectedUserName = getIntent().getStringExtra(UsersTab.SELECTED_USERS_KEY);

        usersPostRecyclerView = findViewById(R.id.recyclerView_UsersPost);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(selectedUserName + "'s Posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>(PHOTO_CLASS_KEY);
        parseQuery.whereEqualTo(USER_NAME_KEY, selectedUserName);
        parseQuery.orderByDescending(CREATED_BY_KEY);

        alertDialog = new SweetAlertDialog(UsersPostActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        usersPostRecyclerView.setAdapter(new UsersPostRecyclerAdapter(objects));
                        usersPostRecyclerView.setLayoutManager(new LinearLayoutManager(UsersPostActivity.this));
                        alertDialog.dismissWithAnimation();

                    } else {

                        alertDialog.dismissWithAnimation();

                        alertDialog = new SweetAlertDialog(UsersPostActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("No Posts!")
                                .setContentText(selectedUserName + " haven't posted yet!...")
                                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                        alertDialog.show();

                    }


                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(UsersPostActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login Fail")
                            .setContentText("Error " + " : " + e.getMessage());
                    alertDialog.show();

                }

            }
        });

    }
}