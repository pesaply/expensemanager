package com.ict.client.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class UserModel {
    /**
     * This is the UserModel. It is used to store three kinds of users : USER, MANAGER, ADMIN
     */
    public static final String USER = "USER", MANAGER = "MANAGER", ADMIN = "ADMIN";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String userId;
    private String email;
    private String username;
    private String mobileNumber;
    private boolean active;
    private String role = UserModel.USER;

    public UserModel() {
    }

    public UserModel(String email, String username, String mobileNumber, boolean active, String role) {
        this.email = email;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.active = active;
        this.role = role;
    }

    public UserModel(String userId, String email, String username, String mobileNumber, boolean active, String role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.active = active;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                '}';
    }
}
