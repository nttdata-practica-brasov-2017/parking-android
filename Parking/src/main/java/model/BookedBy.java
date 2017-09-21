package model;

import java.util.Date;

/**
 * Created by Raluca on 18.09.2017.
 */

public class BookedBy {
    private String username;
    private int spotNumber;
    private int floor;
    private Date date;

    public BookedBy(String username, int spotNumber, int floor, Date date) {
        this.username = username;
        this.spotNumber = spotNumber;
        this.floor = floor;
        this.date = date;
    }

    public BookedBy() {
    }

    public String getUsername() {
        return username;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public int getFloor() {
        return floor;
    }

    public Date getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
