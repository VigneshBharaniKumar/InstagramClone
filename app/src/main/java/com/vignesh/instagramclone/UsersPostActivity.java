package com.vignesh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class UsersPostActivity extends AppCompatActivity {

    private String selectedUserName;

    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        selectedUserName = getIntent().getStringExtra(UsersTab.SELECTED_USERS_KEY);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(selectedUserName);

    }

}
