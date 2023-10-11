package com.cavity.usermanager.account;

import jakarta.persistence.*;

@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    private String username;


    private String pin;


    private String role;

    public Account() {
    }

    public Account(Integer accountId, String username, String pin, String role) {
        this.accountId = accountId;
        this.username = username;
        this.pin = pin;
        this.role = role;
    }

    public Account(String username, String pin, String role) {
        this.username = username;
        this.pin = pin;
        this.role = role;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", pin='" + pin + '\'' +
                ", role=" + role +
                '}';
    }
}