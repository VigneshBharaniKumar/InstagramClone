package com.vignesh.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private TextInputLayout edtCaption;
    private Button btnShare;

    private Bitmap receivedBitmap;

    private SweetAlertDialog alertDialog;

    private static final  String PHOTO_CLASS_KEY = "Photo";
    private static final  String PICTURE_KEY = "picture";
    private static final  String CAPTION_KEY = "caption";
    private static final  String USER_NAME_KEY = "username";

    public SharePictureTab() {
        // Required empty public constructor
    }


    public static SharePictureTab newInstance() {
        SharePictureTab fragment = new SharePictureTab();

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
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgShare = view.findViewById(R.id.imgShare_sharePictureTab);
        edtCaption = view.findViewById(R.id.edtCaption_sharePictureTab);
        btnShare = view.findViewById(R.id.btnShare_sharePictureTab);

        imgShare.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgShare_sharePictureTab:
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                } else {
                    getExternalImage();
                }

                break;

            case R.id.btnShare_sharePictureTab:
                if (receivedBitmap != null) {

                    alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    alertDialog.setTitleText("Loading");
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    receivedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();

                    ParseFile parseFile = new ParseFile("image.png", bytes);
                    ParseObject parseObject = new ParseObject(PHOTO_CLASS_KEY);
                    parseObject.put(PICTURE_KEY, parseFile);
                    parseObject.put(CAPTION_KEY, edtCaption.getEditText().getText().toString());
                    parseObject.put(USER_NAME_KEY, ParseUser.getCurrentUser().getUsername());
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                alertDialog.dismiss();
                                alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Uploaded!")
                                        .setContentText("Your image shared successfully");
                                alertDialog.show();

                            } else {
                                alertDialog.dismiss();
                                alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Failed!")
                                        .setContentText("Failed to upload your image");
                                alertDialog.show();
                            }
                        }
                    });

                } else {

                    alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Failed!")
                            .setContentText("Please select an Image...");
                    alertDialog.show();

                }

                break;

        }

    }

    private void getExternalImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getExternalImage();

            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2000 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                receivedBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ImageView imageView = (ImageView) getActivity().findViewById(R.id.imgShare_sharePictureTab);
                imageView.setImageBitmap(receivedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
