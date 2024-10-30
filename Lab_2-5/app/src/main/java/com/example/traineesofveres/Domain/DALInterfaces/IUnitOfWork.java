package com.example.traineesofveres.Domain.DALInterfaces;

import com.example.traineesofveres.DTO.Infrastructure.Entity;

public interface IUnitOfWork {
    <T extends Entity> IRepository<T> GetRepository();

    void SaveChanges();
}
