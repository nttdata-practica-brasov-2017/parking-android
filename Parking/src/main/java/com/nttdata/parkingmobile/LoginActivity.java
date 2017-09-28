package com.nttdata.parkingmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import manager.DataManager;
import model.Assignment;
import model.User;
import webservice.LoginDelegate;
import webservice.LoginTask;

public class LoginActivity extends Activity implements LoginDelegate {

    private Button btnSignIn;
    private Button btnCancel;
    private EditText editTextUsername, editTextPassword;
    private CheckBox checkBox_RememberMe;
    private String username;
    private String password;
    private String passwordSentToVacancy;
    private User user;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    LoginActivity loginActivity;

    ProgressBar progressBarSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivity = this;

        getLoginPreferences();
        progressBarSpinner = (ProgressBar) findViewById(R.id.progressBar);
        progressBarSpinner.setVisibility(View.GONE);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                passwordSentToVacancy = password;
                progressBarSpinner.setVisibility(View.VISIBLE);

                LoginTask loginTask = new LoginTask(username, password);
                loginTask.setLoginDelegate(loginActivity);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextUsername.setText("");
                editTextPassword.setText("");
                progressBarSpinner.setVisibility(View.INVISIBLE);
            }
            //finish();
        });
    }

    private void startNewActivity(User user) {
        Intent myIntent;

        if (!user.getType().isEmpty() && user.getType().equals("PERMANENT")) {
            myIntent = new Intent(LoginActivity.this, ReleaseActivity.class);
        } else {
            myIntent = new Intent(LoginActivity.this, ClaimActivity.class);
        }
        myIntent.putExtra("username", user.getUsername());
        myIntent.putExtra("password", passwordSentToVacancy);

        startActivity(myIntent);
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
    public void onLoginDone(String result) throws UnsupportedEncodingException {

        progressBarSpinner.setVisibility(View.INVISIBLE);
        Log.d("TAG", "LOGIN DONE DELEGATE " + result);

        if (!result.isEmpty()) {
            User user = DataManager.getInstance().parseUser(result);

            String baseAuthStr = username + ":" + password;
            String str = "Basic " + Base64.encodeToString(baseAuthStr.getBytes("UTF-8"), Base64.DEFAULT);
            DataManager.getInstance().setBaseAuthStr(str);

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
            startNewActivity(user);
        } else {
            Toast.makeText(getApplicationContext(), "Fail login", Toast.LENGTH_SHORT).show();
        }
    }

}