package com.example.traineesofveres.Domain.Services.TraineeService;

import com.example.traineesofveres.DTO.Domain.TraineeModel;

import java.util.ArrayList;

public interface ITraineeService {
    /** Return trainee list with size topCount+1 where last element are trainee with traineeId**/
    ArrayList<TraineeModel> GetTop(int topCount, int traineeId);

    TraineeModel Login(String email, String password);

    TraineeModel Find(int id);

    TraineeModel SignUp(TraineeModel trainee);

    TraineeModel UpdateTrainee(TraineeModel trainee);

    Boolean IsEmailAvailable(int id, String email);

    Boolean isValidEmail(String email);


}
