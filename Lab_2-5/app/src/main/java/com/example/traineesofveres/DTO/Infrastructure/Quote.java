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

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getTraineePublisherId() {
        return TraineePublisherId;
    }

    public void setTraineePublisherId(int traineePublisherId) {
        TraineePublisherId = traineePublisherId;
    }

    public String getDateOfPublication() {
        return DateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        DateOfPublication = dateOfPublication;
    }
}
