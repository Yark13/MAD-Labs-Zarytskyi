package com.example.traineesofveres.Domain.DALInterfaces;

import com.example.traineesofveres.Domain.Models.Model;

public interface IUnitOfWork {
    <T extends Model> IRepository<T> GetRepository();

    void SaveChanges();
}
