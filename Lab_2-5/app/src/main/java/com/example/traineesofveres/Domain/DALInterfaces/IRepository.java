package com.example.traineesofveres.Domain.DALInterfaces;

import com.example.traineesofveres.Domain.Models.Model;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface IRepository<T extends Model> {
    ArrayList<T> GetAll();

    ArrayList<T> GetAll(Predicate<T> filter);

    ArrayList<T> GetAll(int skip, int take);

    ArrayList<T> GetAll(Predicate<T> filter, int skip, int take);

    T Find(int id);

    T Add(T entity);

    T Update(T entity);

    void Delete();
}
