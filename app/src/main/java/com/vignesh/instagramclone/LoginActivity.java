package com.vignesh.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout edtUserName, edtPassword;
    private Button btnLogin, btnSignUp;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Welcome! Login Here");

        edtUserName = findViewById(R.id.edtUserName_login);
        edtPassword = findViewById(R.id.edtPassword_login);
        btnLogin = findViewById(R.id.btnLogin_login);
        btnSignUp = findViewById(R.id.btnSignUp_login);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    login();
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        /*ANIMATION - STARTS*/
        edtUserName.setAlpha(0);
        edtPassword.setAlpha(0);
        btnLogin.setAlpha(0);
        btnSignUp.setAlpha(0);

        edtUserName.animate().alpha(1).setDuration(500);
        edtPassword.animate().alpha(1).setDuration(500);
        btnLogin.animate().alpha(1).setDuration(1000);
        btnSignUp.animate().alpha(1).setDuration(1000);
        /*ANIMATION - ENDS*/

        /*If User already logged in -> Directly navigates to Home activity (No Login Needed)*/
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }

    public void goToSignUp(View view) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void login() {

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

    public void onClickRootLayout(View view) {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
