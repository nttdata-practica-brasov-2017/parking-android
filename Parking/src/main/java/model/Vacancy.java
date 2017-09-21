package model;

import java.util.Date;


public class Vacancy {

    private int spotNumber;
    private Date date;
    private Date vacatedAt;
    private String bookedBy; // username

    public Vacancy(){
    }

    public Vacancy(int spotNumber, Date date, Date vacatedAt, String bookedBy) {
        this.spotNumber = spotNumber;
        this.date = date;
        this.vacatedAt = vacatedAt;
        this.bookedBy = bookedBy;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getVacatedAt() {
        return vacatedAt;
    }

    public void setVacatedAt(Date vacatedAt) {
        this.vacatedAt = vacatedAt;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "spotNumber=" + spotNumber +
                ", date=" + date +
                ", vacatedAt=" + vacatedAt +
                ", bookedBy='" + bookedBy + '\'' +
                '}';
    }
}
