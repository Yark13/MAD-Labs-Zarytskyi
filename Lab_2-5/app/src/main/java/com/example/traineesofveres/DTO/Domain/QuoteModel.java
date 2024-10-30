package com.example.traineesofveres.DTO.Domain;

import android.media.Image;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.DTO.Infrastructure.Quote;

import java.util.Date;
import java.util.Objects;

public class QuoteModel extends Model {
    public Image Photo;

    public String Text;

    public int TraineePublisherId;

    public Date DateOfPublication;

    public QuoteModel(Quote quote) {
        Objects.requireNonNull(quote);

        Id = quote.Id;
        Text = quote.Text;
        TraineePublisherId = quote.TraineePublisherId;
        DateOfPublication = quote.DateOfPublication;
    }
}
