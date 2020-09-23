package com.vignesh.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout edtEmailId, edtUserName, edtPassword;
    private Button btnSignUp;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up Here");

        edtEmailId = findViewById(R.id.edtEmailID_signUp);
        edtUserName = findViewById(R.id.edtUserName_signUp);
        edtPassword = findViewById(R.id.edtPassword_signUp);
        btnSignUp = findViewById(R.id.btnSignUp_signUp);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    signUpNewUser(btnSignUp);
                }
                return false;
            }
        });

    }


    public void signUpNewUser(View view) {

        if (!edtEmailId.getEditText().getText().toString().equals("")) {
            edtEmailId.setError(null);
            if (!edtUserName.getEditText().getText().toString().equals("")) {
                edtUserName.setError(null);
                if (!edtPassword.getEditText().getText().toString().equals("")) {
                    edtPassword.setError(null);

                    alertDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    alertDialog.setTitleText("Loading");
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                    ParseUser parseUser = new ParseUser();
                    parseUser.setEmail(edtEmailId.getEditText().getText().toString());
                    parseUser.setUsername(edtUserName.getEditText().getText().toString());
                    parseUser.setPassword(edtPassword.getEditText().getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            alertDialog.dismiss();

                            if (e == null) {
                                alertDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Sign Up Success")
                                        .setContentText("Account Created Successfully! Please verify your email before Login")
                                        .setConfirmButton("Log In", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                alertDialog.dismissWithAnimation();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            } else {

                                alertDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sign Up Failed")
                                        .setContentText("Error Account Creation failed Account could not be created" + " : " + e.getMessage());
                            /*.setConfirmButton("",null)
                            .setCancelButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    alertDialog.dismissWithAnimation();
                                }
                            });*/
                                alertDialog.show();

                            }

                        }
                    });
                } else edtPassword.setError("Please Enter the Password");
            } else edtUserName.setError("Please Enter the User Name");
        } else edtEmailId.setError("Please Enter the Email ID");
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
