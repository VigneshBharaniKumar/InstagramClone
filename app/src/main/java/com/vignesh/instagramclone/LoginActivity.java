package com.vignesh.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout edtUserName, edtPassword;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Welcome! Login Here");

        edtUserName = findViewById(R.id.edtUserName_login);
        edtPassword = findViewById(R.id.edtPassword_login);

    }

    public void goToSignUp(View view) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void login(View view) {

        alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseUser.logInInBackground(edtUserName.getEditText().getText().toString(), edtPassword.getEditText().getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        alertDialog.dismissWithAnimation();

                        if (user != null && e == null) {

                            alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Login Successful!")
                                    .setContentText("Welcome Home, " + user.getUsername())
                                    .setConfirmButton("Home", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            alertDialog.dismissWithAnimation();
                                        }
                                    });
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                        } else {

                            alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Login Fail")
                                    .setContentText("Error " + " : " + e.getMessage());
                                    /*.setCancelButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            alertDialog.dismissWithAnimation();
                                        }
                                    });*/
                            alertDialog.show();

                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {

    }
}
