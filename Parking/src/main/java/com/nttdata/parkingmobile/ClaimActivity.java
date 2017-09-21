package com.nttdata.parkingmobile;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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

import webservice.VacanciesDelegate;
import webservice.VacanciesTask;

public class ClaimActivity extends AppCompatActivity implements View.OnClickListener, VacanciesDelegate {

    private Button btnBack;
    private Button btnSelectDate;
    private Button btnClaim;
    private EditText txtSelectDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView textAvailableSpots;
    // private Spinner spinnerDate;
    private List<Vacancy> vacancyList;
    private Date dateTime;
    private String username;
    List<String> listDate;
    ListView listView;
    private String selected;
    ClaimActivity claimActivity;
    boolean clickedClaim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        claimActivity = this;

        username = getIntent().getExtras().getString("username");

        TextView textUser = (TextView) findViewById(R.id.textUser);
        textUser.setText("Welcome " + username + " !");

        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(this);
        txtSelectDate = (EditText) findViewById(R.id.txtSelectDate);
        btnClaim = (Button) findViewById(R.id.btnClaim);

        vacancyList = DataManager.getInstance().getVacancyList();

        textAvailableSpots = (TextView) findViewById(R.id.textAvailableSpots);
        listView = (ListView) findViewById(R.id.listView);
        //  spinnerDate = (Spinner) findViewById(R.id.spinnerDate);

        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = new Date();
        try {
            dateTime = fmt.parse(txtSelectDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //daca username-ul logat se afla prin lista de Vacancy, se considera ca a rezervat deja acel loc si nu mai poate face alte actiuni
        for (Vacancy v : DataManager.getInstance().getVacancyList())
            if (v.getBookedBy().equals(username))
                clickedClaim = true;

        addItemsOnListView();
        registerClickCallBack();

        btnBack = (Button) findViewById(R.id.btnBack);


        VacanciesTask vacanciesTask = new VacanciesTask();
        vacanciesTask.setVacanciesDelegate(claimActivity);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start NewActivity.class
                Intent myClaimIntent = new Intent(ClaimActivity.this, LoginActivity.class);
                startActivity(myClaimIntent);
            }
        });

        //if (listDate.isEmpty())
        //  btnClaim.setEnabled(false);


        btnClaim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // String selectedItem = spinnerDate.getSelectedItem().toString();

                clickedClaim = true;
                for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
                    try {
                        if (!selected.isEmpty() &&
                                vacancyList.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) &&
                                vacancyList.getSpotNumber() == Integer.parseInt(selected.toString()) &&
                                vacancyList.getBookedBy().isEmpty()) {
                            vacancyList.setBookedBy(username);
                            Toast.makeText(getApplicationContext(), "Your have booked spot number " + vacancyList.getSpotNumber() +
                                    " on " + txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                addItemsOnListView();


            }
        });
    }

    private void registerClickCallBack() {

        //String selectedItem;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //selected = ((TextView) view.findViewById(R.id.listView)).getText().toString();

                if (!clickedClaim) {
                    selected = listDate.get(position);
                    Toast.makeText(ClaimActivity.this, "You chose: " + selected, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "You already booked a parking space!" +
                            txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();
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
                            addItemsOnListView();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }
    }

    /*private void registerClickCallBack()
    {
        ListView listV=(ListView)findViewById(R.id.ListView);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SecondActivity.this,"You clicked: "+position, Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void addItemsOnListView() {

        btnClaim.setEnabled(true);

        listDate = new ArrayList<String>();
        Integer s;

        if (DataManager.getInstance().getVacancyList().size() > 0) {
            for (Vacancy vacancyList : DataManager.getInstance().getVacancyList())
                try {
                    if (vacancyList.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) && vacancyList.getBookedBy().isEmpty()) {
                        s = new Integer(vacancyList.getSpotNumber());
                        listDate.add(s.toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }

        // listView=(ListView)findViewById(R.id.listView);
        /* ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDate);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dataAdapter);*/

        //Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listDate);
        listView.setAdapter(adapter);


        if (listDate.size() == 0) {
            Toast.makeText(getApplicationContext(), "There are no other parking spots available on " +
                    txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();

            btnClaim.setEnabled(false);
            textAvailableSpots.setText("");
        }

        if (clickedClaim) {
            btnClaim.setEnabled(false);
            textAvailableSpots.setText("");
        }


    }

    public void onVacanciesDone(String result) {
        Log.d("TAG", "VACANCIES DONE DELEGATE " + result);

        DataManager.getInstance().parseVacancies(result);
    }

}
