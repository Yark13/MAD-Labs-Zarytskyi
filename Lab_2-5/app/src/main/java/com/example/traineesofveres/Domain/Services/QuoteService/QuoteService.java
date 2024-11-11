package com.example.traineesofveres.Domain.Services.QuoteService;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.DTO.Domain.QuoteModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuoteService implements IQuoteService {
    public static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final IUnitOfWork _unitOfWork;
    private final IRepository<Quote> _repository;

    public QuoteService(IUnitOfWork unitOfWork) {
        _unitOfWork = Objects.requireNonNull(unitOfWork, "unit of work cannot be null");
        _repository = _unitOfWork.GetRepository(Quote.class);
    }


    @Override
    public ArrayList<QuoteModel> GetQuotes(int skip, int take) {
        ArrayList<Quote> s = _repository.GetAll(skip, take);

        ArrayList<QuoteModel> quoteModels = s.stream().map(QuoteModel::new)
                .collect(Collectors.toCollection(ArrayList::new));

        return quoteModels;
    }

    @Override
    public QuoteModel AddQuote(QuoteModel quote) {
        QuoteModel result = new QuoteModel(
                                _repository.Add(
                                    new Quote(quote)));
        _unitOfWork.SaveChanges();

        return result;
    }
}
