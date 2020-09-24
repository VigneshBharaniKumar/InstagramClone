package com.vignesh.instagramclone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static androidx.core.content.ContextCompat.getSystemService;

public class ProfileTab extends Fragment {

    private TextInputLayout edtName_ProfileTab, edtUserName_ProfileTab, edtWebsite_ProfileTab, edtBio_ProfileTab, edtEmailID_ProfileTab, edtPhoneNumber_ProfileTab, edtGender_ProfileTab;
    private Button btnUpdate_ProfileTab;
    private AutoCompleteTextView genderDropdown_ProfileTab;

    private final ParseUser user = ParseUser.getCurrentUser();

    private SweetAlertDialog alertDialog;

    private String[] gender = {"Male", "Female", "Others", "Prefer Not to Say"};

    private static final String NAME_KEY = "name";
    private static final String USER_NAME_KEY = "username";
    private static final String WEBSITE_KEY = "website";
    private static final String BIO_KEY = "bio";
    private static final String EMAIL_ID_KEY = "email";
    private static final String PHONE_NUMBER_KEY = "phone_number";
    private static final String GENDER_KEY = "gender";

    public ProfileTab() {
        // Required empty public constructor
    }

    public static ProfileTab newInstance() {
        ProfileTab fragment = new ProfileTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtName_ProfileTab = view.findViewById(R.id.edtName_ProfileTab);
        edtUserName_ProfileTab = view.findViewById(R.id.edtUserName_ProfileTab);
        edtWebsite_ProfileTab = view.findViewById(R.id.edtWebsite_ProfileTab);
        edtBio_ProfileTab = view.findViewById(R.id.edtBio_ProfileTab);
        edtEmailID_ProfileTab = view.findViewById(R.id.edtEmailID_ProfileTab);
        edtPhoneNumber_ProfileTab = view.findViewById(R.id.edtPhoneNumber_ProfileTab);
        edtGender_ProfileTab = view.findViewById(R.id.edtGender_ProfileTab);
        genderDropdown_ProfileTab = view.findViewById(R.id.genderDropdown_ProfileTab);
        btnUpdate_ProfileTab = view.findViewById(R.id.btnUpdate_ProfileTab);

        getDataFromServer();

        //edtUserName_ProfileTab.setEnabled(false);
        edtEmailID_ProfileTab.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_menu_popup_item, gender);
        genderDropdown_ProfileTab.setAdapter(adapter);

        btnUpdate_ProfileTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdate();
            }
        });

        return view;

    }

    private void getDataFromServer() {

        edtName_ProfileTab.getEditText().setText((CharSequence) user.get(NAME_KEY + ""));
        edtUserName_ProfileTab.getEditText().setText((CharSequence) user.get(USER_NAME_KEY + ""));
        edtWebsite_ProfileTab.getEditText().setText((CharSequence) user.get(WEBSITE_KEY + ""));
        edtBio_ProfileTab.getEditText().setText((CharSequence) user.get(BIO_KEY + ""));
        edtEmailID_ProfileTab.getEditText().setText((CharSequence) user.get(EMAIL_ID_KEY + ""));
        edtPhoneNumber_ProfileTab.getEditText().setText((CharSequence) user.get(PHONE_NUMBER_KEY + ""));
        genderDropdown_ProfileTab.setText((CharSequence) user.get(GENDER_KEY+""));

    }

    private void onClickUpdate() {

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        user.put(NAME_KEY, edtName_ProfileTab.getEditText().getText().toString());
        user.put(WEBSITE_KEY, edtWebsite_ProfileTab.getEditText().getText().toString());
        user.put(BIO_KEY, edtBio_ProfileTab.getEditText().getText().toString());
        //user.put(EMAIL_ID_KEY, edtEmailID_ProfileTab.getEditText().getText().toString());
        user.put(PHONE_NUMBER_KEY, edtPhoneNumber_ProfileTab.getEditText().getText().toString());
        user.put(GENDER_KEY, Objects.requireNonNull(genderDropdown_ProfileTab.getText().toString()));

        if (!edtUserName_ProfileTab.getEditText().getText().toString().equals("")) {
            user.put(USER_NAME_KEY, edtUserName_ProfileTab.getEditText().getText().toString());
            edtUserName_ProfileTab.setError(null);

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    alertDialog.dismissWithAnimation();

                    if (e == null) {

                        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Updated")
                                .setContentText("Your profile updated successfully! ")
                                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                    }
                                });
                        alertDialog.setCancelable(true);
                        alertDialog.show();

                        getDataFromServer();

                    } else {

                        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Failed")
                                .setContentText("Something went wrong \n Error: " + e)
                                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                    }
                                });
                        alertDialog.setCancelable(true);
                        alertDialog.show();

                    }

                }
            });

        } else {
            alertDialog.dismissWithAnimation();
            edtUserName_ProfileTab.setError("Please Enter the User Name");
        }

    }

}
