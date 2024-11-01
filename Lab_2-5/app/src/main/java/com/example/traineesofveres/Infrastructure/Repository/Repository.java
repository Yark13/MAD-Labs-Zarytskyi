package com.example.traineesofveres.Infrastructure.Repository;

import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.DTO.Infrastructure.Entity;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Repository<T extends Entity> implements IRepository<T> {

    @Override
    public ArrayList<T> GetAll() {
        return null;
    }

    @Override
    public ArrayList<T> GetAll(Predicate<T> filter) {
        return null;
    }

    @Override
    public ArrayList<T> GetAll(int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<T> GetAll(Predicate<T> filter, int skip, int take) {
        return null;
    }

    @Override
    public ArrayList<TraineeModel> GetTopWithRank(int topCount, int traineeId) {
        return null;
    }

    @Override
    public T Find(int id) {
        return null;
    }

    @Override
    public T Add(T entity) {
        return null;
    }

    @Override
    public T Update(T entity) {
        return null;
    }

    @Override
    public void Delete() {

    }
}
