package com.example.traineesofveres.Domain.Services.TraineeService;

import com.example.traineesofveres.Domain.Models.TraineeModel;

import java.util.ArrayList;

public interface ITraineeService {
    ArrayList<TraineeModel> GetTop();

    TraineeModel Login(TraineeModel trainee);

    TraineeModel SignUp(TraineeModel trainee);

    TraineeModel UpdateTrainee(TraineeModel trainee);

    Boolean IsEmailAvailable(String email);
}
