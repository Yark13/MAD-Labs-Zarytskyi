package com.example.traineesofveres.DTO.Domain;

import android.media.Image;

import com.example.traineesofveres.DTO.Infrastructure.Entity;
import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.Domain.Services.QuoteService.QuoteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class QuoteModel extends Model {
    public Image Photo;

    public String Text;

    public int TraineePublisherId;

    public LocalDate  DateOfPublication;

    public QuoteModel(Quote quote) {
        Objects.requireNonNull(quote);

        Id = quote.Id;
        Text = quote.Text;
        TraineePublisherId = quote.TraineePublisherId;
        DateOfPublication = LocalDate.parse(quote.DateOfPublication, QuoteService.Formatter);
    }
}
