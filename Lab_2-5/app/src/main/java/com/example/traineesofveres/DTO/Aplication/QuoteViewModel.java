package com.example.traineesofveres.DTO.Aplication;

import androidx.lifecycle.ViewModel;

public class QuoteViewModel extends ViewModel {
    public int Photo;

    public String Date;

    public String Quote;

    public QuoteViewModel(int photo, String date, String quote) {
        Photo = photo;
        Date = date;
        Quote = quote;
    }

    public int getPhoto() {
        return Photo;
    }

    public String getDate() {
        return Date;
    }

    public String getQuote() {
        return Quote;
    }
}