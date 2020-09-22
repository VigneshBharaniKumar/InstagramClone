package com.vignesh.instagramclone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private EditText edtBoxerName, edtKickSpeed, edtKickPower, edtPunchSpeed, edtPunchPower;
    private Button btnSave, btnGetData;
    private TextView txtGetData;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtBoxerName = findViewById(R.id.edtName);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        btnSave = findViewById(R.id.btnSave);
        btnGetData = findViewById(R.id.btnGetData);
        txtGetData = findViewById(R.id.txtGetData);

    }

    public void save(View view) {

        try {

            final ParseObject object = new ParseObject("KickBoxer");
            object.put("boxer_name", edtBoxerName.getText().toString());
            object.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            object.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));
            object.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            object.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(MainActivity.this, object.get("boxer_name") + " Saved Successfully", Toast.LENGTH_SHORT).show();
                        Toasty.success(MainActivity.this, object.get("boxer_name") + " is saved", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.warning(MainActivity.this, "Failed: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Toasty.error(MainActivity.this, "Error: " + e, Toast.LENGTH_LONG, true).show();
        }
    }

    public void getData(View view) {

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
        parseQuery.getInBackground("kompGYF0QQ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (object != null && e == null) {

                    String getData = "Name - " + object.get("boxer_name") + "\n" +
                            "Kick Speed - " + object.get("kick_speed") + "\n" +
                            "Kick Power - " + object.get("kick_power") + "\n" +
                            "Punch Speed - " + object.get("punch_speed") + "\n" +
                            "Punch Power - " + object.get("punch_power");

                    txtGetData.setText(getData);

                } else {

                    Toasty.error(MainActivity.this, "Object is NULL or Error: " + e, Toast.LENGTH_LONG, true).show();

                }

            }
        });

    }

    public void getAllData(View view) {

        alertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
        parseQuery.whereContains("boxer_name", "Vignesh");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                alertDialog.dismissWithAnimation();
                if (e == null) {

                    if (objects.size() > 0) {

                        String getData = "";

                        for (ParseObject kickBoxer : objects) {

                            getData = getData + "Name - " + kickBoxer.get("boxer_name") + "\n" +
                                    "Kick Speed - " + kickBoxer.get("kick_speed") + "\n" +
                                    "Kick Power - " + kickBoxer.get("kick_power") + "\n" +
                                    "Punch Speed - " + kickBoxer.get("punch_speed") + "\n" +
                                    "Punch Power - " + kickBoxer.get("punch_power") + "\n \n";

                        }

                        TextView txtAllData = new TextView(MainActivity.this);
                        txtAllData.setText(getData);

                        alertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("All Data")
                                .setCustomView(txtAllData)
                                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                    }
                                });
                                alertDialog.show();

                    } else
                        Toasty.warning(MainActivity.this, "Object is NULL", Toast.LENGTH_LONG).show();

                } else
                    Toasty.error(MainActivity.this, "Error: " + e, Toast.LENGTH_LONG, true).show();

            }
        });

    }
}
