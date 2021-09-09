package com.example.attendanceapps;

public class User {
    private String username, password, email, birth;

    public User(String username, String password, String email, String birth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birth = birth;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
