package model;


public class Assignment {

    private String username;
    private int spotNumber;

    public Assignment(String username, int spotNumber) {
        this.username = username;
        this.spotNumber = spotNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "username='" + username + '\'' +
                ", spotNumber=" + spotNumber +
                '}';
    }
}
