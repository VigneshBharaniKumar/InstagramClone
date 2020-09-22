package com.vignesh.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnLogOut;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home");

        txtWelcome = findViewById(R.id.txtWelcomeHome);
        btnLogOut = findViewById(R.id.btnLogOut);

        txtWelcome.setText("Welcome Home, " + ParseUser.getCurrentUser().getUsername());

    }

    public void logOut(View view) {

        alertDialog = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are You Sure?")
                .setContentText("Ok...Bye-bye then")
                .setConfirmButton("Bye", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismissWithAnimation();
                        ParseUser.logOut();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {

    }
}
