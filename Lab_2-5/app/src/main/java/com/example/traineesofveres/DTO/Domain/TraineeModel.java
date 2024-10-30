package com.example.traineesofveres.DTO.Domain;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;

import java.util.Objects;

public class TraineeModel extends Model{
    public String Name;

    public String Surname;

    public String Email;

    public String Password;

    public int Age;

    public int Score;

    public int Rank;

    public TraineeModel(Trainee trainee) {
        Objects.requireNonNull(trainee);

        Id = trainee.Id;
        Name = trainee.Name;
        Surname = trainee.Surname;
        Email = trainee.Email;
        Password = trainee.Password;
        Age = trainee.Age;
        Score = trainee.Score;
    }
}
