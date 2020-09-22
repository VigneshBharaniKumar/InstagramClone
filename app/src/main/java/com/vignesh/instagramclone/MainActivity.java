package com.vignesh.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private EditText edtBoxerName, edtKickSpeed, edtKickPower, edtPunchSpeed, edtPunchPower;
    private Button btnSave, btnGetData;
    private TextView txtGetData;

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

    public void save (View view) {

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
                        Toasty.success(MainActivity.this, object.get("boxer_name") + " is saved", Toasty.LENGTH_SHORT,true).show();
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
}
