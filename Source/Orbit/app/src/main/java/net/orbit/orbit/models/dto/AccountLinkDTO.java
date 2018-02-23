package net.orbit.orbit.models.dto;

/**
 * Created by brocktubre on 12/25/17.
 */

public class AccountLinkDTO {
    private int userID;
    private int studentID;

    public AccountLinkDTO(int userID, int studentID) {
        this.userID = userID;
        this.studentID = studentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "AccountLinkDTO{" +
                "userID='" + userID + '\'' +
                ", studentID='" + studentID + '\'' +
                '}';
    }

}
