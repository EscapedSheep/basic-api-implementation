package com.thoughtworks.rslist.domain;

import javax.validation.constraints.*;

public class User {
    @NotNull
    @Size(max=8)
    private String userName;

    @NotNull
    private String gender;

    @NotNull
    @Min(18)
    @Max(100)
    private int age;

    @Pattern(regexp = "1\\d{10}")
    private String phone;

    @Email
    private String email;

    private int votes;

    public User(String userName, String gender, int age, String phone, String email) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.votes = 10;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
