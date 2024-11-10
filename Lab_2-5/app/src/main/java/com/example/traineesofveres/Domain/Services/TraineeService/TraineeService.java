package com.example.traineesofveres.Domain.Services.TraineeService;

import android.nfc.FormatException;

import com.example.traineesofveres.DTO.Domain.QuoteModel;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.Security.IPasswordManager;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.DTO.Domain.TraineeModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class TraineeService implements ITraineeService{
    private final IUnitOfWork _unitOfWork;
    private final IRepository<Trainee> _repository;
    private final IPasswordManager _passwordManager;

    public TraineeService(IUnitOfWork unitOfWork, IPasswordManager passwordManager) {
        _unitOfWork = Objects.requireNonNull(unitOfWork);
        _passwordManager = Objects.requireNonNull(passwordManager);

        _repository = _unitOfWork.GetRepository(Trainee.class);
    }

    @Override
    public ArrayList<TraineeModel> GetTop(int topCount, int traineeId) {

        if(topCount < 1 || traineeId < 1)
            throw  new NullPointerException("top count and trainee id cannot be less 1");

        return _repository.GetTopWithRank(topCount, traineeId);
    }

    @Override
    public TraineeModel Login(String email, String password) {
        if(IsNullOrEmpty(email) || IsNullOrEmpty(password))
            throw  new NullPointerException("email and password cannot be null or empty");
        
        String hashedPassword = _passwordManager.HashPassword(password);
        Predicate<Trainee> filter = trainee ->
                trainee.Email.equals(email) &&
                trainee.Password.equals(hashedPassword);

        List<Trainee> trainees = _repository.GetAll(filter);

        return (trainees.size() == 0)? null : new TraineeModel(trainees
                        .get(0));
    }

    @Override
    public TraineeModel Find(int id) {
        if(id <= 0)
            throw  new ArrayIndexOutOfBoundsException();

        return new TraineeModel(_repository.Find(id));
    }

    @Override
    public TraineeModel SignUp(TraineeModel trainee) {
        Objects.requireNonNull(trainee);

        if(!IsTraineeModelCorrectlyFilled(trainee) || trainee.Id != 0)
            return null;

        TraineeModel result = new TraineeModel(
                                    _repository.Add(
                                            new Trainee(trainee)));
        _unitOfWork.SaveChanges();

        return result;
    }

    @Override
    public TraineeModel UpdateTrainee(TraineeModel trainee) {
        Objects.requireNonNull(trainee);

        if(!IsTraineeModelCorrectlyFilled(trainee) || trainee.Id == 0)
            throw new InvalidParameterException("Invalid filled credential");

        if(!IsEmailAvailable(trainee.Id, trainee.Email))
            throw new IllegalArgumentException("Email is already taken");

        TraineeModel result = new TraineeModel(
                                    _repository.Update(
                                            new Trainee(trainee)));
        _unitOfWork.SaveChanges();

        return result;
    }

    @Override
    public Boolean IsEmailAvailable(int id, String email) {
        if(IsNullOrEmpty(email))
            throw new NullPointerException("email cannot be null");
            
        return _repository.GetAll(trainee -> trainee.Email.equals(email) && trainee.Id != id).size() == 0;
    }

    @Override
    public Boolean isValidEmail(String email) {
        if (IsNullOrEmpty(email)) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }

    private Boolean IsTraineeModelCorrectlyFilled(TraineeModel traineeModel){

        return IsAllTraineeFieldsFilled((traineeModel))
                && isValidEmail(traineeModel.Email)
                && (traineeModel.Age > 14 && traineeModel.Age < 100);
    }
    
    private Boolean IsAllTraineeFieldsFilled(TraineeModel trainee){
        return !(IsNullOrEmpty(trainee.Name)
                || IsNullOrEmpty(trainee.Surname)
                || IsNullOrEmpty(trainee.Email)
                || IsNullOrEmpty(trainee.Password)
                || trainee.Age == 0);
    }

    private Boolean IsNullOrEmpty(String string){
        return string == null || string.isEmpty();
    }
}
