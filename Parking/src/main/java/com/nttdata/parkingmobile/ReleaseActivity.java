package com.nttdata.parkingmobile;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import manager.DataManager;
import model.Vacancy;
import webservice.BookedTask;
import webservice.LoginTask;
import webservice.ReleaseDelegate;
import webservice.ReleaseTask;
import webservice.VacanciesTask;

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener, ReleaseDelegate {

    private Button btnRelease;
    private Button btnDatePicker;
    private EditText txtDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Date date;
    private Date vacatedAt;
    private String username;
    private ReleaseActivity releaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        username = getIntent().getExtras().getString("username");

        btnDatePicker = (Button) findViewById(R.id.btnSelectDate);
        txtDate = (EditText) findViewById(R.id.txtSelectDate);
        btnRelease = (Button) findViewById(R.id.btnRelease);
        btnRelease.setVisibility(View.INVISIBLE);

        releaseActivity = this;

        btnDatePicker.setOnClickListener(this);

        username = getIntent().getExtras().getString("username");

        TextView textUser = (TextView) findViewById(R.id.textUser);
        textUser.setText("Welcome " + username + " !");

        btnRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vacancy vacancy = new Vacancy();
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
                date = new Date();

                try {
                    if (!txtDate.getText().toString().isEmpty()) {
                        date = fmt.parse(txtDate.getText().toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!txtDate.getText().toString().isEmpty()) {
                    vacatedAt = Calendar.getInstance().getTime();

                    ReleaseTask releaseTask = new ReleaseTask(username, date, vacatedAt);
                    releaseTask.setReleaseDelegate(releaseActivity);

                    Toast.makeText(getApplicationContext(), "You have released you spot on " + fmt.format(date), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select the date!", Toast.LENGTH_SHORT).show();
                }
                btnRelease.setText("RELEASED");
                btnRelease.setEnabled(false);
            }
        });
    }

    //Date picker
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {
            btnRelease.setText("Release");
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            btnRelease.setVisibility(View.VISIBLE);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @Override
    public void onReleaseDone(String result) {
    }

    @Override
    public void onReleaseError(String response) {
        Toast.makeText(getApplicationContext(), "You already reserved a spot!!", Toast.LENGTH_SHORT).show();
    }
}