package manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Assignment;
import model.Spot;
import model.User;
import model.Vacancy;

/**
 * Created by m09ny on 09/11/17.
 */

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager() {
        Log.d("TAG", "DataManager()");
    }

    private List<User> userList;
    private List<Assignment> assignmentList;
    private List<Spot> spotList;
    private List<Vacancy> vacancyList;

    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }
    public void setAssignmentList(List<Assignment> assignmentList) { this.assignmentList = assignmentList; }

    public List<Spot> getSpotList() {
        return spotList;
    }
    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public List<Vacancy> getVacancyList() { return vacancyList; }
    public void setVacancyList(List<Vacancy> vacancyList) { this.vacancyList = vacancyList; }

    Context context;

    public User parseUser(String inputJSON) {

        User user = new User();

        try {
            JSONObject jsonObject = new JSONObject(inputJSON);
            Log.d("TAG", "jsonObject - " + String.valueOf(jsonObject));

            user.setUsername(jsonObject.getString("username"));
            user.setFirstName(jsonObject.getString("firstName"));
            user.setLastName(jsonObject.getString("lastName"));
            user.setType(jsonObject.getString("type"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }


    public List<Vacancy> parseVacancies(String inputJSON) {

        ArrayList<Vacancy> vacancyList = new ArrayList<Vacancy>();

        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i=0 ; i<jsonArray.length() ; i++){
                Vacancy vacancy = new Vacancy();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject spotObject = jsonObject.getJSONObject("spot");
                Log.d("TAG", "JSONObject - " + String.valueOf(jsonObject));
                Log.d("TAG", "spot - " + String.valueOf(spotObject));

                vacancy.setSpotNumber(spotObject.getInt("number"));

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);


                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
                Date dateTime = new Date();
                Date vacatedAtdate = new Date();
                Date bookedBydate = new Date();


                try {
                    dateTime = fmt.parse(String.valueOf(jsonObject.get("date")));
                    vacatedAtdate = fmt.parse(String.valueOf(jsonObject.get("vacatedAt")));
                    bookedBydate = fmt.parse(String.valueOf(jsonObject.get("bookedBy")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vacancy.setDate(dateTime);
                vacancy.setDate(bookedBydate);

                if (calendar.getTime().after(vacatedAtdate) ){
                    vacancy.setDate(vacatedAtdate);
                }
                else {
                    Toast.makeText(context, "You cannot select a date before today :) ", Toast.LENGTH_SHORT).show();
                }

                //add object to list
                vacancyList.add(vacancy);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vacancyList;
    }



}
