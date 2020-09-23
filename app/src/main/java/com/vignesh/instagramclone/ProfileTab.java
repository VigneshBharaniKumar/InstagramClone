package com.vignesh.instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileTab extends Fragment {

    private TextInputLayout edtName_ProfileTab, edtUserName_ProfileTab, edtWebsite_ProfileTab, edtBio_ProfileTab, edtEmailID_ProfileTab, edtPhoneNumber_ProfileTab, edtGender_ProfileTab;
    private Button btnUpdate_ProfileTab;
    private AutoCompleteTextView genderDropdown_ProfileTab;

    private final ParseUser user = ParseUser.getCurrentUser();

    private SweetAlertDialog alertDialog;

    private String[] gender = {"Male", "Female", "Others", "Prefer Not to Say"};

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

        edtName_ProfileTab.getEditText().setText((CharSequence) user.get("name" + ""));
        edtUserName_ProfileTab.getEditText().setText((CharSequence) user.get("username" + ""));
        edtWebsite_ProfileTab.getEditText().setText((CharSequence) user.get("website" + ""));
        edtBio_ProfileTab.getEditText().setText((CharSequence) user.get("bio" + ""));
        edtEmailID_ProfileTab.getEditText().setText((CharSequence) user.get("email" + ""));
        edtPhoneNumber_ProfileTab.getEditText().setText((CharSequence) user.get("phone_number" + ""));
        //edtGender_ProfileTab.getEditText().setText((CharSequence) user.get("gender" + ""));
        genderDropdown_ProfileTab.setText((CharSequence) user.get("gender"+""));

    }

    private void onClickUpdate() {

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        user.put("name", edtName_ProfileTab.getEditText().getText().toString());
        //user.put("username", edtUserName_ProfileTab.getEditText().getText().toString());
        user.put("website", edtWebsite_ProfileTab.getEditText().getText().toString());
        user.put("bio", edtBio_ProfileTab.getEditText().getText().toString());
        //user.put("email", edtEmailID_ProfileTab.getEditText().getText().toString());
        user.put("phone_number", edtPhoneNumber_ProfileTab.getEditText().getText().toString());
        user.put("gender", Objects.requireNonNull(genderDropdown_ProfileTab.getText().toString()));


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

    }

}
