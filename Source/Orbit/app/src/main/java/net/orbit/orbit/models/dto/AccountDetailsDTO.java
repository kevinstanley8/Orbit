package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.User;

/**
 * Created by brocktubre on 2/5/18.
 */

public class AccountDetailsDTO {
    private User user;
    private String firstName;
    private String lastName;

    private String dob;
    // TODO add more details about users address
    // and other info we want to store

    public AccountDetailsDTO(User user, String fName, String lName, String dob){
        this.user = user;
        this.firstName = fName;
        this.lastName = lName;
        this.dob = dob;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
