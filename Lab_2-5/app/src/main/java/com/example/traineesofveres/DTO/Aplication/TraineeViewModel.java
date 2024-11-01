package com.example.traineesofveres.DTO.Aplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class TraineeViewModel extends ViewModel {
    public int Id;

    public String Name;

    public String Surname;

    public int Age;

    public  int Score;

    public TraineeViewModel(int id, String name, String surname, int age, int score, @NonNull AutoCloseable... closeables) {
        super(closeables);
        Id = id;
        Name = name;
        Surname = surname;
        Age = age;
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public int getAge() {
        return Age;
    }

    public int getScore() {
        return Score;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setScore(int score) {
        Score = score;
    }
}
