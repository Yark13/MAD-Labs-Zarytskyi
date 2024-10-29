package com.example.traineesofveres.Domain.Services.QuoteService;

import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.Domain.Models.QuoteModel;

import java.util.ArrayList;
import java.util.Objects;

public class QuoteService implements IQuoteService {
    private final IUnitOfWork _unitOfWork;
    private final IRepository<QuoteModel> _repository;

    public QuoteService(IUnitOfWork unitOfWork) {
        _unitOfWork = Objects.requireNonNull(unitOfWork, "unit of work cannot be null");
        _repository = _unitOfWork.GetRepository();
    }


    @Override
    public ArrayList<QuoteModel> GetQuotes(int skip, int take) {
        return _repository.GetAll(skip, take);
    }

    @Override
    public QuoteModel AddQuote(QuoteModel quote) {
        QuoteModel result = _repository.Add(quote);
        _unitOfWork.SaveChanges();

        return result;
    }
}
