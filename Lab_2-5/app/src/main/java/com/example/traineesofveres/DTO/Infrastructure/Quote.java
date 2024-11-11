package com.example.traineesofveres.DTO.Infrastructure;

import com.example.traineesofveres.DTO.Domain.QuoteModel;
import com.example.traineesofveres.Domain.Services.QuoteService.QuoteService;

import java.util.Date;
import java.util.Objects;

public class Quote extends Entity{
    public String Text;

    public int TraineePublisherId;

    public String DateOfPublication;

    public Quote(){

    }

    public Quote(QuoteModel quoteModel) {
        Objects.requireNonNull(quoteModel);

        Id = quoteModel.Id;
        Text = quoteModel.Text;
        TraineePublisherId = quoteModel.TraineePublisherId;
        DateOfPublication = quoteModel.DateOfPublication.format(QuoteService.Formatter);
    }
}
