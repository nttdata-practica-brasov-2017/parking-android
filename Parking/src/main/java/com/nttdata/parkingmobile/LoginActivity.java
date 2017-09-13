package com.nttdata.parkingmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import manager.DataManager;
import model.Assignment;
import model.User;
import webservice.LoginDelegate;
import webservice.LoginTask;

public class LoginActivity extends Activity implements LoginDelegate{

    private Button btnSignIn;
    private Button btnCancel;
    private EditText editTextUsername, editTextPassword;
    private CheckBox checkBox_RememberMe;
    private String username, password;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivity = this;

        DataManager.getInstance().getUserList();
        DataManager.getInstance().getSpotList();
        DataManager.getInstance().getAssignmentList();

        getLoginPreferences();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                if (checkCredentials(username, password) == true) {
                    if (checkBox_RememberMe.isChecked()) {
                        // remember username and password
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    startNewActivity();
                } else {
                    if (username.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                    }
                    if (password.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    }
                    if (!username.equals("admin") || !password.equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
                //Log.i(TAG,"USERNAME: "+username);
                LoginTask loginTask = new LoginTask(username, password);
                loginTask.setDelegate(loginActivity);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextUsername.setText("");
                editTextPassword.setText("");
            }
            //finish();
        });
    }

    private void startNewActivity() {
        boolean userWithSpot = false;
        int assignedSpot = 0;

        Intent myIntent;

        List<Assignment> assignmentList = DataManager.getInstance().getAssignmentList();

        for (Assignment userWithAssignedSpot : assignmentList) {
            //check if user has an assigned spot.
            if (userWithAssignedSpot.getUsername().equals(username)) {
                userWithSpot = true;
                assignedSpot = userWithAssignedSpot.getSpotNumber();
            }

            if (userWithSpot) {
                myIntent = new Intent(LoginActivity.this, ReleaseActivity.class);
            } else {
                myIntent = new Intent(LoginActivity.this, ClaimActivity.class);
            }

            // Store value at the time of the login attempt.
            myIntent.putExtra("username", editTextUsername.getText().toString());
            myIntent.putExtra("assignedSpot", assignedSpot);
            startActivity(myIntent);
        }
    }

    public Boolean checkCredentials(String username, String password) {
        List<User> userList = DataManager.getInstance().getUserList();

        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public void getLoginPreferences() {

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        checkBox_RememberMe = (CheckBox) findViewById(R.id.rememberMe);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            checkBox_RememberMe.setChecked(true);
        }
    }


    @Override
    public void onLoginDone(String result) {
        Log.d("TAG" , "LOGIN DONE DELEGATE " + result);
    }
}