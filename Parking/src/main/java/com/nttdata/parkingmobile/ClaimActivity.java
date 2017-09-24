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
import model.Spot;
import model.User;
import model.Vacancy;

import webservice.ClaimDelegate;
import webservice.ClaimTask;
import webservice.LoginTask;
import webservice.VacanciesDelegate;
import webservice.VacanciesTask;

public class ClaimActivity extends AppCompatActivity implements View.OnClickListener, VacanciesDelegate , ClaimDelegate{

    private Button btnBack;
    private Button btnSelectDate;
    private Button btnClaim;
    private EditText txtSelectDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView textAvailableSpots;
    private Date dateTime;
    private String username;
    private String password;
    private int spotNumber;
    private int floor;
    private List<String> listSpotsVacanted;
    private List<Spot> listSpots;
    private ListView listView;
    private String selected;
    private ClaimActivity claimActivity;
    boolean clickedClaim = false;
    private List<Vacancy> vacancyList;
    private List<Spot> spotsList;
    private Spot spotClaimed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        claimActivity = this;

        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");

        TextView textUser = (TextView) findViewById(R.id.textUser);
        textUser.setText("Welcome " + username + " !");

        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(this);
        txtSelectDate = (EditText) findViewById(R.id.txtSelectDate);
        btnClaim = (Button) findViewById(R.id.btnClaim);
        btnBack = (Button) findViewById(R.id.btnBack);

        textAvailableSpots = (TextView) findViewById(R.id.textAvailableSpots);
        listView = (ListView) findViewById(R.id.listView);

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
                clickedClaim = true;

                for (Vacancy vacancySpotClaimed : vacancyList)
                    try {

                        // spotNumber = vacancySpotClaimed.getSpot().getSpotNumber();
                        //floor= vacancySpotClaimed.getSpot().getFloor();


                        /*if (!selected.isEmpty() &&
                                vacancySpotClaimed.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) &&
                              //  vacancySpotClaimed.getSpot().getSpotNumber() == Integer.parseInt(selected) &&
                                vacancySpotClaimed.getSpot().getSpotNumber() == spotNumber &&
                                vacancySpotClaimed.getBookedBy() == null) {
                            vacancySpotClaimed.setBookedBy(username);
                            Toast.makeText(ClaimActivity.this, "Your have booked spot number " + vacancySpotClaimed.getSpot().getSpotNumber() +
                                    " on " + txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            // TODO aici trebuie sa fac claimTask pentru eliberarea locului rezervat din vacancyList

                           // spotNumber = Integer.parseInt(selected);
                            //floor= vacancySpotClaimed.getSpot().getFloor();

                            ClaimTask claimTask = new ClaimTask(username, spotNumber, floor, dateTime);
                            claimTask.setClaimDelegate((ClaimDelegate) claimActivity); //TODO cast la ClaimDelegate (posibil sa nu fie corect)

                        }*/
                        if (!selected.isEmpty() &&
                                vacancySpotClaimed.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) &&
                                //  vacancySpotClaimed.getSpot().getSpotNumber() == Integer.parseInt(selected) &&
                                vacancySpotClaimed.getSpot().getSpotNumber() == spotClaimed.getSpotNumber() &&
                                vacancySpotClaimed.getSpot().getFloor() == spotClaimed.getFloor() &&
                                vacancySpotClaimed.getBookedBy() == null) {
                            vacancySpotClaimed.setBookedBy(username);
                            Toast.makeText(ClaimActivity.this, "Your have booked spot number " + vacancySpotClaimed.getSpot().getSpotNumber() +
                                    " on " + txtSelectDate.getText().toString(), Toast.LENGTH_SHORT).show();
                            // TODO aici trebuie sa fac claimTask pentru eliberarea locului rezervat din vacancyList

                            // spotNumber = Integer.parseInt(selected);
                            //floor= vacancySpotClaimed.getSpot().getFloor();

                            ClaimTask claimTask = new ClaimTask(username, spotClaimed.getSpotNumber(), spotClaimed.getFloor(), dateTime);
                            claimTask.setClaimDelegate(claimActivity); //TODO cast la ClaimDelegate (posibil sa nu fie corect)

                            VacanciesTask vacanciesTask = new VacanciesTask(username, password, dateTime);
                            vacanciesTask.setVacanciesDelegate(claimActivity);

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
                if (!clickedClaim) {
                    selected = listSpotsVacanted.get(position);
                    Toast.makeText(ClaimActivity.this, "You chose: " + selected, Toast.LENGTH_LONG).show();
                    int indexStopNumber = selected.indexOf("at");
                    int indexStartFloor = selected.indexOf("r")+2;
                    Log.d("TAG", "indexStopNumber: " + indexStopNumber);
                    Log.d("TAG", "indexStartFloor: " + indexStartFloor);
                    int nrSpot = Integer.parseInt(selected.substring(6, indexStopNumber - 1));
                    Log.d("TAG", "nrSpot: " + nrSpot);
                    // int nrFloor = Integer.parseInt(selected.substring(indexStartFloor, selected.length()));
                    int nrFloor=Integer.parseInt(selected.charAt(selected.length()-1)+"");
                    Log.d("TAG", "nrFloor: " + nrFloor);
                    spotClaimed = new Spot(nrSpot, nrFloor);


                } else
                    Toast.makeText(ClaimActivity.this, "You already booked a parking space!" +
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

                            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy"); //TODO

                            dateTime = new Date();
                            try {
                                dateTime = fmt.parse(txtSelectDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            VacanciesTask vacanciesTask = new VacanciesTask(username, password, dateTime);
                            vacanciesTask.setVacanciesDelegate(claimActivity);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void addItemsOnListView() {
        btnClaim.setEnabled(true);
        // listSpots = new ArrayList<String>();

        // listSpots = new ArrayList<Spot>();
        listSpotsVacanted = new ArrayList<>();
        if (vacancyList.size() > 0) {
            for (Vacancy vacancySpot : vacancyList)
                try {
                    //new SimpleDateFormat("dd-MM-yyyy")
                    if (vacancySpot.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse(txtSelectDate.getText().toString())) &&
                            vacancySpot.getBookedBy() == null) {
                        listSpotsVacanted.add("Spot #" + vacancySpot.getSpot().getSpotNumber() + " at floor " + vacancySpot.getSpot().getFloor());


                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            //Build adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSpotsVacanted);
            listView.setAdapter(adapter);

            if (listSpotsVacanted.size() == 0 && !txtSelectDate.getText().toString().isEmpty()) {
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
    }

    public void onVacanciesDone(String result) {
        Log.d("TAG", "VACANCIES DONE DELEGATE " + result);
        vacancyList = DataManager.getInstance().parseVacancies(result);


        DataManager.getInstance().setVacancyList(vacancyList);

        //daca username-ul logat se afla prin lista de Vacancy, se considera ca a rezervat deja acel loc si nu mai poate face alte actiuni
        for (Vacancy v : vacancyList)
            if (v.getBookedBy() != null && v.getBookedBy().equals(username))
                clickedClaim = true;

        addItemsOnListView();
        registerClickCallBack();
    }

    @Override
    public void onClaimDone(String result) {

    }
}
