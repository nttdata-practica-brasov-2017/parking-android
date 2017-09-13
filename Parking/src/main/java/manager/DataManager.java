package manager;

import android.util.Log;

import java.util.ArrayList;
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
    public void setAssignmentList(List<Assignment> assignmentList) { this.assignmentList = assignmentList; }

    public List<Spot> getSpotList() {
        return spotList;
    }
    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public List<Vacancy> getVacancyList() { return vacancyList; }
    public void setVacancyList(List<Vacancy> vacancyList) { this.vacancyList = vacancyList; }

    private void createUserList() {
        userList = new ArrayList<User>();
        userList.add(new User("admin", "admin", "admin", "admin", true));
        userList.add(new User("user1", "user1", "user1", "user1", true));
        userList.add(new User("user2", "user2", "user2", "user2", true));
        userList.add(new User("user3", "user3", "user3", "user3", true));
        userList.add(new User("user4", "user4", "user4", "user4", false));
        userList.add(new User("user5", "user5", "user5", "user5", false));
        userList.add(new User("user6", "user6", "user6", "user6", false));
        userList.add(new User("user7", "user7", "user7", "user7", false));
    }

    private void createAssignmentList() {
        assignmentList = new ArrayList<Assignment>();
        assignmentList.add(new Assignment("admin", 1));
        assignmentList.add(new Assignment("user1", 2));
        assignmentList.add(new Assignment("user2", 3));
        assignmentList.add(new Assignment("user3", 4));
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


}
