package manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private DataManager() {

        createUserList();
        createSpotList();
        createAssignmentList();
        setVacancyList(new ArrayList<Vacancy>());

        Log.d("TAG", "DataManager()");
    }

    public void ceva() {
        Log.d("TAG", "ceva()");
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

    private void createUserList() {
        userList = new ArrayList<User>();
        userList.add(new User("admin", "admin", "admin", "admin", true));
        userList.add(new User("user1", "user1", "user1", "user1", true));
        userList.add(new User("user2", "user2", "user2", "user2", true));
        userList.add(new User("user3", "user3", "user3", "user3", true));
        userList.add(new User("user4", "user4", "user4", "user4", true));
        userList.add(new User("user5", "user5", "user5", "user5", true));
        userList.add(new User("user6", "user6", "user6", "user6", true));
        userList.add(new User("user7", "user7", "user7", "user7", true));
        userList.add(new User("user8", "user8", "user8", "user8", false));
        userList.add(new User("user9", "user9", "user9", "user9", false));
        userList.add(new User("user10", "user10", "user10", "user10", false));
        userList.add(new User("user11", "user11", "user11", "user11", false));
        userList.add(new User("user12", "user12", "user12", "user12", false));
    }

    private void createAssignmentList() {
        assignmentList = new ArrayList<Assignment>();
        assignmentList.add(new Assignment("admin", 1));
        assignmentList.add(new Assignment("user1", 2));
        assignmentList.add(new Assignment("user2", 3));
        assignmentList.add(new Assignment("user3", 4));
        assignmentList.add(new Assignment("user4", 5));
        assignmentList.add(new Assignment("user5", 6));
        assignmentList.add(new Assignment("user6", 7));
        assignmentList.add(new Assignment("user7", 8));

    }

    private void createSpotList() {
        spotList = new ArrayList<Spot>();
        spotList.add(new Spot(1, 1));
        spotList.add(new Spot(2, 1));
        spotList.add(new Spot(3, 1));
        spotList.add(new Spot(4, 1));
        spotList.add(new Spot(5, 1));
        spotList.add(new Spot(6, 1));
        spotList.add(new Spot(7, 1));
        spotList.add(new Spot(8, 1));
        spotList.add(new Spot(9, 1));
        spotList.add(new Spot(10, 2));
        spotList.add(new Spot(11, 2));
        spotList.add(new Spot(12, 2));
        spotList.add(new Spot(13, 2));
        spotList.add(new Spot(14, 2));
        spotList.add(new Spot(15, 2));
        spotList.add(new Spot(16, 2));
        spotList.add(new Spot(17, 2));
        spotList.add(new Spot(18, 2));
        spotList.add(new Spot(19, 2));
        spotList.add(new Spot(20, 2));
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
                Log.d("TAG", "JSONObject - " + String.valueOf(jsonObject));
                Log.d("TAG", "spot - " + String.valueOf(spotObject));

                vacancy.setSpotNumber(spotObject.getInt("number"));

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
                vacancy.setDate(vacatedAtdate);
                vacancy.setDate(bookedBydate);
                vacancyList.add(vacancy);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vacancyList;
    }


}
