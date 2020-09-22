package com.vignesh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout edtEmailId, edtUserName, edtPassword;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up Here");

        edtEmailId = findViewById(R.id.edtEmailID_signUp);
        edtUserName = findViewById(R.id.edtUserName_signUp);
        edtPassword = findViewById(R.id.edtPassword_signUp);

    }


    public void signUpNewUser(View view) {

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

    }
}
