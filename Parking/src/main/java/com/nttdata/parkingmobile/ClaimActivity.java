package com.nttdata.parkingmobile;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import manager.DataManager;
import model.Vacancy;

public class ClaimActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBack, btnSelectDate, btnClaim;
    EditText txtSelectDate;
    private int mYear, mMonth, mDay;
    TextView textAvailableSpots;
    private Spinner spinnerDate;
    List<Vacancy> vacancyList;
    private Date dateTime;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        username = getIntent().getExtras().getString("username");

        TextView textUser = (TextView) findViewById(R.id.textUser);
        textUser.setText("Welcome " + username + " !");

        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(this);
        txtSelectDate = (EditText) findViewById(R.id.txtSelectDate);
        btnClaim = (Button) findViewById(R.id.btnClaim);

        vacancyList = DataManager.getInstance().getVacancyList();

        textAvailableSpots = (TextView) findViewById(R.id.textAvailableSpots);
        spinnerDate = (Spinner) findViewById(R.id.spinnerDate);

        btnBack = (Button) findViewById(R.id.btnBack);

        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = new Date();
        try {
            dateTime = fmt.parse(txtSelectDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                Intent myClaimIntent = new Intent(ClaimActivity.this, LoginActivity.class);
                startActivity(myClaimIntent);
            }
        });

        btnClaim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String selectedItem = spinnerDate.getSelectedItem().toString();

                for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
                    try {
                        if (!selectedItem.isEmpty() &&
                                vacancyList.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) &&
                                vacancyList.getSpotNumber() == Integer.parseInt(selectedItem.toString()) &&
                                vacancyList.getBookedBy().isEmpty()) {
                            vacancyList.setBookedBy(username);
                            Toast.makeText(getApplicationContext(), "Your have booked spot number " + vacancyList.getSpotNumber() +
                                    " on " + txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    //Date picker
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == btnSelectDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            txtSelectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            textAvailableSpots.setText("Here are the available spots for " + txtSelectDate.getText().toString());
                            addItemsOnSpinnerDate();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }
    }

    public void addItemsOnSpinnerDate() {

        List<String> listDate = new ArrayList<String>();
        Integer s;

        for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
            try {
                if (vacancyList.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) && vacancyList.getBookedBy().isEmpty()) {
                    s = new Integer(vacancyList.getSpotNumber());
                    listDate.add(s.toString());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDate);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dataAdapter);

    }

}
