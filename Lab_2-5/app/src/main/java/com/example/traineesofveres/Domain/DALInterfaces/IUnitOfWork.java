package com.example.traineesofveres.Domain.DALInterfaces;

import com.example.traineesofveres.DTO.Infrastructure.Entity;

public interface IUnitOfWork extends AutoCloseable {
    <T extends Entity> IRepository<T> GetRepository(Class<T> classT);

    void SaveChanges();
}
