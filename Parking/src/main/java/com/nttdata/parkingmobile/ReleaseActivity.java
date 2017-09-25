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

    private Button btnBack;
    private Button btnRelease;
    private Button btnDatePicker;
    private EditText txtDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    //private int spotNumber = 0;
    //private int floor=-1;
    private Date date;
    private Date vacatedAt;
    private String username;
    private int assignedSpot;
    private ReleaseActivity releaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        username = getIntent().getExtras().getString("username");

        btnDatePicker = (Button) findViewById(R.id.btnSelectDate);
        txtDate = (EditText) findViewById(R.id.txtSelectDate);
        btnRelease = (Button) findViewById(R.id.btnRelease);

        releaseActivity = this;

        btnDatePicker.setOnClickListener(this);

        username = getIntent().getExtras().getString("username");
        assignedSpot = getIntent().getExtras().getInt("assignedSpot");

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
                //vacancy.setSpotNumber(assignedSpot);

                //set vacated date
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
                //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                //Date currentTime=Calendar.getInstance().getTime();
                date = new Date();

                try {
                    date = fmt.parse(txtDate.getText().toString());


                } catch (ParseException e) {
                    e.printStackTrace();
                }


                // vacancy.setDate(vacatedDate);

                // set vacated at
                //vacatedAt = new Date();
                vacatedAt=Calendar.getInstance().getTime();
                String datetime = fmt.format(vacatedAt);

               /* try {
                    vacancy.setVacatedAt(fmt.parse(datetime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                // set booked by
                // vacancy.setBookedBy("");

                Boolean alreadyReleased = false;

              /*  for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
                    if (vacancyList.getDate().equals(vacancy.getDate()) && vacancyList.getSpotNumber() == vacancy.getSpotNumber()) {
                        alreadyReleased = true;
                    }
*/
                if (!alreadyReleased) {
                    //add to vacancyList
                    //  DataManager.getInstance().getVacancyList().add(vacancy);

                    ReleaseTask releaseTask = new ReleaseTask(username, date, vacatedAt);
                    releaseTask.setReleaseDelegate(releaseActivity);

                   // Toast.makeText(getApplicationContext(), "You have released spot number " + assignedSpot +
                      //      " on " + fmt.format(vacancy.getDate()), Toast.LENGTH_SHORT).show();


               // } else
                 //   Toast.makeText(getApplicationContext(), "Your spot number " + assignedSpot +
                //            " is already released on " + fmt.format(vacancy.getDate()), Toast.LENGTH_SHORT).show();

                }
            }
        });


        // BookedTask bookedTask = new BookedTask(username, spotNumber, floor, date);
        // bookedTask.setDelegate(releaseActivity);
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

    @Override
    public void onReleaseDone(String result) {

    }
}