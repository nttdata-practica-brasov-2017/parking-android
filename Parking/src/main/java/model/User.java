package model;

/**
 * Created by m09ny on 09/08/17.
 */

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String type;

    public User(){
    }

    public User(String username, String firstName, String lastName, String type){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
