package com.example.traineesofveres.DTO.Domain;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;

import java.io.Serializable;
import java.util.Objects;
public class TraineeModel extends Model implements Serializable {
    private String _defaultValue = "-";

    public String Name = _defaultValue;

    public String Surname = _defaultValue;

    public String Email = _defaultValue;

    public String Password = _defaultValue;

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
