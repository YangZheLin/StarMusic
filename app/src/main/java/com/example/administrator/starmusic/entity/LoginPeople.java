package com.example.administrator.starmusic.entity;

/**
 * Created by Administrator on 2017/2/20.
 */

public class LoginPeople {
    private String UserName;
    private int VipGrade;
    private int UserAge;
    private int UserSex;
    private String UserPass;
    private String UserNumber;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setVipGrade(int vipGrade) {
        VipGrade = vipGrade;
    }

    public void setUserAge(int userAge) {
        UserAge = userAge;
    }

    public void setUserSex(int userSex) {
        UserSex = userSex;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public int getVipGrade() {
        return VipGrade;
    }

    public int getUserAge() {
        return UserAge;
    }

    public int getUserSex() {
        return UserSex;
    }

    public String getUserPass() {
        return UserPass;
    }

    public String getUserNumber() {
        return UserNumber;
    }

}
