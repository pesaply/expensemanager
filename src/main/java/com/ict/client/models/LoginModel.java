package com.ict.client.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class LoginModel {

    /**
     * This is the Login Model. It stores the email and password for all the users: USER, MANAGER , ADMIN
     */

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String loginId;
    private String email;
    private String password;

    public LoginModel() {
    }

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginModel(String loginId, String email, String password) {
        this.loginId=loginId;
        this.email = email;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
