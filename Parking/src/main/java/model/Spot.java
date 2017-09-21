package model;


public class Spot {

    private int spotNumber;
    private int floor;

    public Spot(int number, int floor) {
        this.spotNumber = number;
        this.floor = floor;
    }

    public int getNumber() {
        return spotNumber;
    }

    public void setNumber(int number) {
        this.spotNumber = number;
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
