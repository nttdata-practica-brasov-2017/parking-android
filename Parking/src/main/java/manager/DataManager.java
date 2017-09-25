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
    private String baseAuthStr;

    public DataManager(Date date, List<User> userList, List<Assignment> assignmentList, List<Spot> spotList, List<Vacancy> vacancyList, String baseAuthStr, Context context) {
        this.date = date;
        this.userList = userList;
        this.assignmentList = assignmentList;
        this.spotList = spotList;
        this.vacancyList = vacancyList;
        this.baseAuthStr = baseAuthStr;
        this.context = context;
    }

    public String getBaseAuthStr() {
        return baseAuthStr;
    }

    public void setBaseAuthStr(String baseAuthStr) {
        this.baseAuthStr = baseAuthStr;
    }

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

    public List<Vacancy> parseVacancies(String inputJSON) {

        ArrayList<Vacancy> vacancyList = new ArrayList<Vacancy>();

        try {
            JSONArray jsonArray = new JSONArray(inputJSON);
            Log.d("TAG", "JSONArray - " + String.valueOf(jsonArray));

            for (int i = 0; i < jsonArray.length(); i++) {
                Vacancy vacancy = new Vacancy();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject spotObject = jsonObject.getJSONObject("spot");
                Spot spot = new Spot(new Integer(spotObject.getInt("number")), spotObject.getInt("floor"));

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

                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
                String dateTime1 = fmtOut.format(dateTime);

                try {
                    vacancy.setDate(fmtOut.parse(dateTime1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Date vacatedAtdate = new Date();
                //Date bookedBydate = new Date();

                //add object to list
                vacancyList.add(vacancy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vacancyList;
    }
}
