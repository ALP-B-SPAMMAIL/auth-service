package com.example.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String userFigureId;
    public String name;
    public String job;
    public String gender;
    public String birthDate;
    public String password;
    public String interest;
    public String email;

    public int getId(){
        return this.id;
    }
    public String getPassword() {
        return this.password;
    }
    public String getName() {
        return this.name;
    }
    public String getUserFigureId() {
        return this.userFigureId;
    }

    public void setPassword(String password) {
        this.password=password;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setJob(String job) {
        this.job=job;
    }
    public void setInterest(String interest) {
        this.interest=interest;
    }
    public void setGender(String gender) {
        this.gender=gender;
    }
    public void setBirthDate(String birth) {
        this.birthDate=birth;
    }
    public void setUserFigureId(String userFigureId) {
        this.userFigureId=userFigureId;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public String getEmail() {
        return this.email;
    }   
}