package com.example.logintest.domain;

public class User {
    private String userId;
    private String userPwd;
    private boolean idCheck;

    public User(String userId, String userPwd, boolean idCheck){
        this.userId=userId;
        this.userPwd=userPwd;
        this.idCheck=idCheck;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public boolean isIdCheck() {
        return idCheck;
    }

    public void setIdCheck(boolean idCheck) {
        this.idCheck = idCheck;
    }
}
