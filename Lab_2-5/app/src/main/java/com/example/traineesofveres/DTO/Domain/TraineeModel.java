package com.example.traineesofveres.DTO.Domain;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;

import java.io.Serializable;
import java.util.Objects;

public class TraineeModel extends Model implements Serializable {
    public String Name;

    public String Surname;

    public String Email;

    public String Password;

    public int Age;

    public int Score;

    public int Rank;

    public TraineeModel(){

    }

    public TraineeModel(Trainee trainee) {
        Id = trainee.Id;
        Name = trainee.Name;
        Surname = trainee.Surname;
        Email = trainee.Email;
        Password = trainee.Password;
        Age = trainee.Age;
        Score = trainee.Score;
    }

    public TraineeModel(Trainee trainee, int rank){
        this(trainee);
        Rank = rank;
    }
}
