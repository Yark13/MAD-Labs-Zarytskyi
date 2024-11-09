package com.example.traineesofveres.DTO.Infrastructure;

import com.example.traineesofveres.DTO.Domain.QuoteModel;

import java.util.Date;
import java.util.Objects;

public class Quote extends Entity{
    public String Text;

    public int TraineePublisherId;

    public Date DateOfPublication;

    public Quote(QuoteModel quoteModel) {
        Objects.requireNonNull(quoteModel);

        Id = quoteModel.Id;
        Text = quoteModel.Text;;
        DateOfPublication = quoteModel.DateOfPublication;
    }
}
