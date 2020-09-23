package com.vignesh.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ProfileTab extends Fragment {

    private TextInputLayout edtName_ProfileTab, edtUserName_ProfileTab, edtWebsite_ProfileTab, edtBio_ProfileTab
            , edtEmailID_ProfileTab, edtPhoneNumber_ProfileTab, edtGender_ProfileTab;
    private Button btnUpdate_ProfileTab;
    private AutoCompleteTextView genderDropdown_ProfileTab;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_menu_popup_item, gender);
        genderDropdown_ProfileTab.setAdapter(adapter);

        return view;

    }
}
