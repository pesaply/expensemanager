package com.ict.client.models;

public class UserReceiverWithId extends UserReceiver{

    private String userId;

    public UserReceiverWithId(String userId, String email, String username, String mobileNumber, boolean active, String role, String password) {
        super(email, username, mobileNumber, active, role, password);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
