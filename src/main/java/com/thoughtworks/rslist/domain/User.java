package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
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

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("user_gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("user_age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("user_phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("user_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public int getVotes() {
        return votes;
    }

    @JsonIgnore
    public void setVotes(int votes) {
        this.votes = votes;
    }

    public UserDto toUserDto() {
        return UserDto.builder().age(age).email(email).userName(userName).gender(gender).phone(phone).votes(votes).build();
    }
}
