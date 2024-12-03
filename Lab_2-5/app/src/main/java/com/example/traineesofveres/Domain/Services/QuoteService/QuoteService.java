package com.example.traineesofveres.Domain.Services.QuoteService;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.Domain.Connection.ConnectionManager.IConnectionManager;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.example.traineesofveres.Domain.DALInterfaces.IUnitOfWork;
import com.example.traineesofveres.DTO.Domain.QuoteModel;

import java.security.InvalidParameterException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuoteService implements IQuoteService {
    public static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final IUnitOfWork _unitOfWork;
    private final IRepository<Quote> _repository;
    private final IConnectionManager _connectionManager;
    private final ArrayList<QuoteModel> _emptyQuoteList;

    public QuoteService(IUnitOfWork unitOfWork, IConnectionManager connectionManager) {
        _unitOfWork = Objects.requireNonNull(unitOfWork, "unit of work cannot be null");
        _connectionManager = Objects.requireNonNull(connectionManager, "ConnectionManager cannot be null");

        _repository = _unitOfWork.GetRepository(Quote.class);

        _emptyQuoteList = new ArrayList<>();
        QuoteModel emptyQuote = new QuoteModel();
        _emptyQuoteList.add(emptyQuote);
        _emptyQuoteList.add(emptyQuote);
        _emptyQuoteList.add(emptyQuote);
        _emptyQuoteList.add(emptyQuote);
        _emptyQuoteList.add(emptyQuote);
    }


    @Override
    public boolean IsConnection() {
        return _connectionManager.isInternetAvailable();
    }

    @Override
    public ArrayList<QuoteModel> GetQuotes(int skip, int take) {
        if(!_connectionManager.isInternetAvailable()) return _emptyQuoteList;

        ArrayList<QuoteModel> quoteModels = _repository.GetAll(skip, take)
                .stream().map(QuoteModel::new)
                .collect(Collectors.toCollection(ArrayList::new));

        return quoteModels;
    }

    @Override
    public QuoteModel AddQuote(QuoteModel quote) {
        ValidateQuote(quote);

        QuoteModel result = new QuoteModel(
                                _repository.Add(
                                    new Quote(quote)));
        _unitOfWork.SaveChanges();

        return result;
    }

    private void ValidateQuote(QuoteModel quote){
        Objects.requireNonNull(quote);

        String validateMessage = "";

        if (quote.Text.isEmpty()) validateMessage += "Quote are empty!\n";
        if(quote.TraineePublisherId <=0) validateMessage += "Cannot determinate who is publisher!\n";
        if(quote.DateOfPublication == null) validateMessage += "Cannot determinate date of quote!\n";

        if(!validateMessage.isEmpty()) throw new InvalidParameterException(validateMessage);
    }
}
