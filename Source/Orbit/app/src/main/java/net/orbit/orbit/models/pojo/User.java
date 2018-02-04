package net.orbit.orbit.models.pojo;


/**
 * Created by sristic on 12/4/17.
 */

public class User {

    private int userID;
    private String email;
    private String uid;
    private String lastLogin;
    private int invalidAttempts;
    private String active;
    private Role role;

    public User () {

    }

    public User(String email, String uid, String lastLogin, int invalidAttempts, String active, Role role) {
        this.email = email;
        this.uid = uid;
        this.lastLogin = lastLogin;
        this.invalidAttempts = invalidAttempts;
        this.active = active;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getInvalidAttempts() {
        return invalidAttempts;
    }

    public void setInvalidAttempts(int invalidAttempts) {
        this.invalidAttempts = invalidAttempts;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                ", lastLogin=" + lastLogin +
                ", invalidAttempts=" + invalidAttempts +
                ", active='" + active + '\'' +
                ", role=" + role +
                '}';
    }
}
