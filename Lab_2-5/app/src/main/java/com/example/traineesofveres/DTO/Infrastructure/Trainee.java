package com.example.traineesofveres.DTO.Infrastructure;

import com.example.traineesofveres.DTO.Domain.TraineeModel;

import java.util.Objects;

public class Trainee extends Entity {
    public String Name = "";

    public String Surname = "";

    public String Email = "";

    public String Password = "";

    public int Age = 0;

    public int Score = 0;

    public Trainee() {
    }

    public Trainee(TraineeModel traineeModel) {
        Objects.requireNonNull(traineeModel);

        Id = traineeModel.Id;
        Name = traineeModel.Name;
        Surname = traineeModel.Surname;
        Email = traineeModel.Email;
        Password = traineeModel.Password;
        Age = traineeModel.Age;
        Score = traineeModel.Score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
