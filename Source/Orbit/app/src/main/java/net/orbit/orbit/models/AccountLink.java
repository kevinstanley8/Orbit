package net.orbit.orbit.models;

import java.sql.Date;

/**
 * Created by brocktubre on 12/25/17.
 */

public class AccountLink {
    private int accountLinkID;
    private Date date_linked;
    private String active;
    private int user_id;
    private int student_id;
    private String message;

    public AccountLink(int accountLinkID, Date date_linked, String active, int user_id, int student_id, String message) {
        this.accountLinkID = accountLinkID;
        this.date_linked = date_linked;
        this.active = active;
        this.user_id = user_id;
        this.student_id = student_id;
        this.message = message;
    }

    public int getAccountLinkID() {
        return accountLinkID;
    }

    public void setAccountLinkID(int accountLinkID) {
        this.accountLinkID = accountLinkID;
    }

    public Date getDate_linked() {
        return date_linked;
    }

    public void setDate_linked(Date date_linked) {
        this.date_linked = date_linked;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AccountLink{" +
                "accountLinkID='" + accountLinkID + '\'' +
                ", date_linked='" + date_linked + '\'' +
                ", active='" + active + '\'' +
                ", user_id='" + user_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
