package com.example.administrator.starmusic.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/26.
 */

public class Customer implements Serializable {
    private String petName;
    private String dengJi;
    private String guanZhu;
    private String fengSi;
    private String qq;
    private String Name;
    private String age;
    private String sex;
    private String phoneNumber;
    private String address;
    private String job;
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDengJi() {
        return dengJi;
    }

    public void setDengJi(String dengJi) {
        this.dengJi = dengJi;
    }

    public String getGuanZhu() {
        return guanZhu;
    }

    public void setGuanZhu(String guanZhu) {
        this.guanZhu = guanZhu;
    }

    public String getFengSi() {
        return fengSi;
    }

    public void setFengSi(String fengSi) {
        this.fengSi = fengSi;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

}
