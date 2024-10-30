package com.example.traineesofveres.Infrastructure.UnitOfWork;

import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.DTO.Infrastructure.Entity;

public class UnitOfWork implements IUnitOfWork {
    @Override
    public <T extends Entity> IRepository<T> GetRepository() {
        return null;
    }

    @Override
    public void SaveChanges() {

    }
}
