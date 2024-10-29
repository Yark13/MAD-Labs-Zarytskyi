package com.example.traineesofveres.Domain.Services.TraineeService;

import com.example.traineesofveres.Domain.Models.TraineeModel;

import java.util.ArrayList;
import java.util.regex.Pattern;

public interface ITraineeService {
    /// Return trainee list with size topCount+1 where last element are trainee with traineeId
    ArrayList<TraineeModel> GetTop(int topCount, int traineeId);

    TraineeModel Login(String email, String password);

    TraineeModel SignUp(TraineeModel trainee);

    TraineeModel UpdateTrainee(TraineeModel trainee);

    Boolean IsEmailAvailable(String email);

    Boolean isValidEmail(String email);


}
