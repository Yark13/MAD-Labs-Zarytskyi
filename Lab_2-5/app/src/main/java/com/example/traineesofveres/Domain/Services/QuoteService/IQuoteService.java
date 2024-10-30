package com.example.traineesofveres.Domain.Services.QuoteService;

import com.example.traineesofveres.DTO.Domain.QuoteModel;

import java.util.ArrayList;

public interface IQuoteService {
    ArrayList<QuoteModel> GetQuotes(int skip, int take);

    QuoteModel AddQuote(QuoteModel quote);
}
