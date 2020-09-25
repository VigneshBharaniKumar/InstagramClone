package com.vignesh.instagramclone;

import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UsersTab extends Fragment implements UsersRecyclerAdapter.OnClickUserInterface {

    private ListView listView;
    private ArrayList usersList;
    private ArrayAdapter adapter;

    private RecyclerView usersRecyclerView;
    private TextView txtLoading;

    private static final String NAME_KEY = "name";
    private static final String USER_NAME_KEY = "username";
    private static final String WEBSITE_KEY = "website";
    private static final String BIO_KEY = "bio";
    private static final String EMAIL_ID_KEY = "email";
    private static final String PHONE_NUMBER_KEY = "phone_number";
    private static final String GENDER_KEY = "gender";

    public static final String SELECTED_USERS_KEY = "selected_user";

    private SweetAlertDialog alertDialog;

    public UsersTab() {
        // Required empty public constructor
    }

    public static UsersTab newInstance() {
        UsersTab fragment = new UsersTab();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab_recycler_view, container, false);
        //View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView = view.findViewById(R.id.listView_UsersTab);
        usersRecyclerView = view.findViewById(R.id.recyclerView_UsersTab);
        txtLoading = view.findViewById(R.id.txtLoading);

        usersList = new ArrayList();
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, usersList);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo(USER_NAME_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {

                    if (users.size() > 0) {

                        for (ParseUser user : users) {

                            usersList.add(user.getUsername());

                        }

                        //listView.setAdapter(adapter);

                        txtLoading.animate().alpha(0).setDuration(1000);

                        usersRecyclerView.setAdapter(new UsersRecyclerAdapter(usersList, UsersTab.this));
                        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }

                }

            }
        });

        return view;
    }


    /*INTERFACE METHOD*/
    @Override
    public void onClickUser(String selectedUser) {
        showUsersPost(selectedUser);
    }

    @Override
    public void onLongClickUser(String selectedUser) {
        showUserInfo(selectedUser);
    }

    private void showUsersPost (String selectedUser) {

        Intent intent = new Intent(getContext(), UsersPostActivity.class);
        intent.putExtra(SELECTED_USERS_KEY, selectedUser);
        startActivity(intent);

    }

    private void showUserInfo(String selectedUser) {

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo(USER_NAME_KEY, selectedUser);
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null) {

                    if (user != null) {

                        alertDialog.dismissWithAnimation();

                        String userData = "Name : " + user.get(NAME_KEY) + "\n" +
                                "Website : " + user.get(WEBSITE_KEY) + "\n" +
                                "Bio : " + user.get(BIO_KEY) + "\n" +
                                "Phone Number : " + user.get(PHONE_NUMBER_KEY) + "\n" +
                                "Email : " + user.get(EMAIL_ID_KEY) + "\n" +
                                "Gender : " + user.get(GENDER_KEY);

                        TextView txtUserData = new TextView(getContext());
                        txtUserData.setText(userData);

                        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(user.getUsername() + "'s Info")
                                .setCustomView(txtUserData);
                        alertDialog.show();

                    } else {

                        alertDialog.dismissWithAnimation();
                        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("User Details")
                                .setContentText("User not Found!");
                        alertDialog.show();

                    }

                } else {

                    alertDialog.dismissWithAnimation();
                    alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("User Details")
                            .setContentText("Error " + " : " + e.getMessage());
                    alertDialog.show();

                }

            }
        });

    }

}
