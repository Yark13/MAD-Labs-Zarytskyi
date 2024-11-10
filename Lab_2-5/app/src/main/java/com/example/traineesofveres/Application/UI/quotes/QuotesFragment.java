package com.example.traineesofveres.Application.UI.quotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineesofveres.Application.UI.adapters.QuotesAdapter;
import com.example.traineesofveres.DTO.Aplication.QuoteViewModel;
import com.example.traineesofveres.Domain.Services.QuoteService.IQuoteService;
import com.example.traineesofveres.R;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuotesFragment extends Fragment {

    @Inject
    IQuoteService _service;

    private ArrayList<QuoteViewModel> _quotesModels = new ArrayList<QuoteViewModel>();

    public static QuotesFragment newInstance() {
        return new QuotesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quotes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.QuotesRecyclerView);

        SetUpQuotesModels();

        QuotesAdapter adapter = new QuotesAdapter(getContext(), _quotesModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    private void SetUpQuotesModels(){
        _quotesModels.clear();
        String[] quotes = getResources().getStringArray(R.array.quotes);
        String[] dates = getResources().getStringArray(R.array.dates);

        for(int i = 0; i < quotes.length; i++){
            _quotesModels.add(new QuoteViewModel(R.mipmap.icon_foreground, dates[i], quotes[i]));
        }

    }

}