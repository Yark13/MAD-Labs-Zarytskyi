package com.example.traineesofveres.DTO.Infrastructure;

import com.example.traineesofveres.DTO.Domain.TraineeModel;

import java.util.Objects;

public class Trainee extends Entity {
    private static int _numberTrainees = 1;

    public String Name;

    public String Surname;

    public String Email;

    public String Password;

    public int Age;

    public int Score;

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

    public static int GetNewId(){
        _numberTrainees++;
        return _numberTrainees;
    }
}
