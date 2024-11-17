package com.ict.client.models;
import com.ict.client.models.UserModel;

public class UserReceiver {

    /**
     * This class is used to store the request body for new user signup/update
     * This class has the password too because the UserModel entity does not store the password.
     */

    private String email;
    private String username;
    private String mobileNumber;
    private boolean active;
    private String role = UserModel.USER;
    private String password;

    public UserReceiver() {
    }

    public UserReceiver(String email, String username, String mobileNumber, boolean active, String role, String password) {
        this.email = email;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.active = active;
        this.role = role;
        this.password = password;
    }

    public UserModel getUserModel()
    {
        return new UserModel(email,username, mobileNumber, active, role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
