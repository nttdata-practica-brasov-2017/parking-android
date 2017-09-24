package model;

import java.util.Date;


public class Vacancy {

    private Spot spot;
    private Date date;
    private Date vacatedAt;
    private String bookedBy; // username

    public Vacancy() {
    }

    public Vacancy(Spot spot, Date date, Date vacatedAt, String bookedBy) {
        this.spot = spot;
        this.date = date;
        this.vacatedAt = vacatedAt;
        this.bookedBy = bookedBy;
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
                "spot=" + spot +
                ", date=" + date +
                ", vacatedAt=" + vacatedAt +
                ", bookedBy='" + bookedBy + '\'' +
                '}';
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Spot getSpot() {
        return spot;
    }
}
