package net.orbit.orbit.models.pojo;

/**
 * Created by brocktubre on 2/5/18.
 */

public class AccountDetailsDTO {
    private User user;
    private String firstName;
    private String lastName;
    // TODO add more details about users address
    // and other info we want to store

    public AccountDetailsDTO(User user, String fName, String lName){
        this.user = user;
        this.firstName = fName;
        this.lastName = lName;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
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
}
