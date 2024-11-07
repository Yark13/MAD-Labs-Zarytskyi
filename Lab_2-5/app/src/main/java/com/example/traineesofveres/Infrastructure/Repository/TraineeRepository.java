package com.example.traineesofveres.Infrastructure.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;

import java.util.ArrayList;
import java.util.function.Predicate;

public class TraineeRepository extends Repository<Trainee> implements IRepository<Trainee> {
    public TraineeRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public ArrayList<Trainee> GetAll() {
        return null;
    }

    @Override
    public ArrayList<Trainee> GetAll(Predicate<Trainee> filter) {
        return null;
    }

    @Override
    public ArrayList<Trainee> GetAll(int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<Trainee> GetAll(Predicate<Trainee> filter, int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<TraineeModel> GetTopWithRank(int topCount, int traineeId) {
        return null;
    }

    @Override
    public Trainee Find(int id) {
        return null;
    }

    @Override
    public Trainee Add(Trainee entity) {
        return null;
    }

    @Override
    public Trainee Update(Trainee entity) {
        return null;
    }

    @Override
    public void Delete() {

    }
}
