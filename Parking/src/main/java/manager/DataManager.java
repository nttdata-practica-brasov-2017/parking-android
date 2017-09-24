package manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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


public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private Date date;
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

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

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

    public Vacancy parseVacancy(String inputJSON) {

        Vacancy vacancy = new Vacancy();

        try {
            JSONObject jsonObject = new JSONObject(inputJSON);
            Log.d("TAG", "jsonObject - " + String.valueOf(jsonObject));


            String dateJson=jsonObject.getString("Date");
            DateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            try {
                date =  df.parse(dateJson);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String vacatedAtJson=jsonObject.getString("vacatedAt");
            DateFormat df2 = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            try {
                date =  df2.parse(vacatedAtJson);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vacancy.setBookedBy(jsonObject.getString("username"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vacancy;
    }


    public List<Vacancy> parseVacancies(String inputJSON) {

        ArrayList<Vacancy> vacancyList = new ArrayList<Vacancy>();

        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i = 0; i < jsonArray.length(); i++) {
                Vacancy vacancy = new Vacancy();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject spotObject = jsonObject.getJSONObject("spot");
                Spot spot = new Spot(new Integer(spotObject.getInt("number")),spotObject.getInt("floor"));
              //  spot.setSpotNumber(new Integer(spotObject.getInt("number")));
               // spot.setFloor(spotObject.getInt("floor"));
                Log.d("TAG", "JSONObject - " + String.valueOf(jsonObject));

                Log.d("TAG", "spot - " + String.valueOf(spotObject));

                vacancy.setSpot(spot);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);


                String strDate = jsonObject.getString("date");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date dateTime = null;


                try {
                    dateTime = format.parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //TODO de ce folosim doua tipuri de formatare a datei
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
                String dateTime1 = fmtOut.format(dateTime);

                try {
                    vacancy.setDate(fmtOut.parse(dateTime1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date vacatedAtdate = new Date();
                Date bookedBydate = new Date();

/*
                try {
                    dateTime = fmt.parse();
                    vacatedAtdate = fmt.parse(String.valueOf(jsonObject.get("vacatedAt")));
                    bookedBydate = fmt.parse(String.valueOf(jsonObject.get("bookedBy")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                vacancy.setDate(bookedBydate);

                if (calendar.getTime().after(vacatedAtdate) ){
                    vacancy.setDate(vacatedAtdate);
                }
                else {
                    Toast.makeText(context, "You cannot select a date before today :) ", Toast.LENGTH_SHORT).show();
                }
*/
                //add object to list
                vacancyList.add(vacancy);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vacancyList;
    }


}
