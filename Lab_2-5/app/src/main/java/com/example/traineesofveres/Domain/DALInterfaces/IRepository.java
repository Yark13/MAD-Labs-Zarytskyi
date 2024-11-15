package com.example.traineesofveres.Domain.DALInterfaces;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.DTO.Infrastructure.Entity;

import java.util.ArrayList;
import java.util.function.Predicate;

import kotlin.Pair;

public interface IRepository<T extends Entity> {
    ArrayList<T> GetAll();

    ArrayList<T> GetAll(Predicate<T> filter);

    ArrayList<T> GetAll(int skip, int take);

    ArrayList<Pair<T, Integer>> GetTopWithRank(int topCount, int traineeId);

    T Find(int id);

    T Add(T entity);

    T Update(T entity);
}
