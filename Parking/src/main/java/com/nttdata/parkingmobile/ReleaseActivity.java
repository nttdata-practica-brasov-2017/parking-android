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

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnBack, btnRelease, btnDatePicker;
    EditText txtDate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        btnDatePicker = (Button) findViewById(R.id.btnSelectDate);
        txtDate = (EditText) findViewById(R.id.txtSelectDate);
        btnRelease = (Button) findViewById(R.id.btnRelease);

        btnDatePicker.setOnClickListener(this);

        String username = getIntent().getExtras().getString("username");
        final int assignedSpot = getIntent().getExtras().getInt("assignedSpot");

        TextView textUser = (TextView) findViewById(R.id.textUser);
        textUser.setText("Welcome " + username + " !");


        TextView textAssignedSpot = (TextView) findViewById(R.id.textAssignedSpot);
        textAssignedSpot.setText(" Your assigned spot is " + assignedSpot);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                Intent myReleaseIntent = new Intent(ReleaseActivity.this, LoginActivity.class);
                startActivity(myReleaseIntent);
            }
        });

        btnRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vacancy vacancy = new Vacancy();
                // set assigned spot
                vacancy.setSpotNumber(assignedSpot);

                //set vacated date
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
                Date vacatedDate = new Date();

                try {
                    vacatedDate = fmt.parse(txtDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vacancy.setDate(vacatedDate);

                // set vacated at
                Date vacatedAt = new Date();
                String datetime = fmt.format(vacatedAt);

                try {
                    vacancy.setVacatedAt(fmt.parse(datetime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // set booked by
                vacancy.setBookedBy("");

                Boolean alreadyReleased = false;

                for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
                    if (vacancyList.getDate().equals(vacancy.getDate()) && vacancyList.getSpotNumber() == vacancy.getSpotNumber()) {
                        alreadyReleased = true;
                    }

                if (!alreadyReleased) {
                    //add to vacancyList
                    DataManager.getInstance().getVacancyList().add(vacancy);

                    Toast.makeText(getApplicationContext(), "You have released spot number " + assignedSpot +
                            " on " + fmt.format(vacancy.getDate()), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Your spot number " + assignedSpot +
                            " is already released on " + fmt.format(vacancy.getDate()), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    //Date picker
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

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
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}