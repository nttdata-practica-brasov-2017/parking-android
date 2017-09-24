package model;


import java.util.Date;

public class Spot {

    private int spotNumber;
    private int floor;

    public Spot(int number, int floor) {
        this.spotNumber = number;
        this.floor = floor;
    }

    public Spot() {
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int number) {
        this.spotNumber = spotNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "spotNumber=" + spotNumber +
                ", floor=" + floor +
                '}';
    }


}
