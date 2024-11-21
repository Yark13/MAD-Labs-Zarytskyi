package com.example.traineesofveres.Application.UI.quotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traineesofveres.Application.UI.adapters.QuotesAdapter;
import com.example.traineesofveres.DTO.Aplication.QuoteViewModel;
import com.example.traineesofveres.DTO.Domain.QuoteModel;
import com.example.traineesofveres.Domain.Services.QuoteService.IQuoteService;
import com.example.traineesofveres.Domain.Services.QuoteService.QuoteService;
import com.example.traineesofveres.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuotesFragment extends Fragment {

    @Inject
    IQuoteService _service;

    private static final String ARG_TRAINEE_ACCOUNT = "ARG_TRAINEE_ACCOUNT";
    private static final int PAGE_SIZE = 10;
    private boolean isLoading = false;
    private int currentPage = 0;

    private int _publisherId;

    private Button _addQuoteButton;
    private EditText _quoteEditText;
    private RecyclerView _recyclerView;
    private QuotesAdapter _adapter;

    public static QuotesFragment newInstance(int traineeId) {
        QuotesFragment fragment = new QuotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRAINEE_ACCOUNT, traineeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _publisherId = (int) getArguments().getSerializable(ARG_TRAINEE_ACCOUNT);
        }

        currentPage = 0;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quotes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FindingViewElements(view);

        SetBehaviorAddButton();

        AdjustRecyclerView();

        LoadQuotes();
    }

    private void SetBehaviorAddButton(){
        _addQuoteButton.setOnClickListener(view ->{
            String newQuoteText = _quoteEditText.getText().toString().trim();

            if(newQuoteText.isEmpty()){
                Toast.makeText(getContext(), R.string.empty_quote_message, Toast.LENGTH_SHORT).show();
                return;
            }

            QuoteModel newQuote = new QuoteModel(newQuoteText, _publisherId, LocalDate.now());

            try {
                _service.AddQuote(newQuote);
                Toast.makeText(getContext(), "New quote added successfully", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FindingViewElements(View view){
        _recyclerView = view.findViewById(R.id.quoteFragment_QuotesRecyclerView);
        _addQuoteButton = view.findViewById(R.id.quoteFragment_ButtonAdd);
        _quoteEditText = view.findViewById(R.id.quoteFragment_EditTextNewQuote);
    }

    private void AdjustRecyclerView(){
        _adapter = new QuotesAdapter(getContext());
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdjustRecyclerViewScrollListener();
    }

    private void AdjustRecyclerViewScrollListener(){
        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                        int totalItemCount = layoutManager.getItemCount();

                        if (lastVisibleItemPosition == totalItemCount - 1 && !isLoading) {
                            LoadQuotes();
                        }
                    }
                }
            }
        });
    }

    private void LoadQuotes(){
        CheckUserConnection();

        isLoading = true;
        int numberItemsCurrentPage = _adapter.getItemCount()%PAGE_SIZE;
        int numberItemsToWholePage = PAGE_SIZE - numberItemsCurrentPage;

        ArrayList<QuoteModel> newQuotes = new ArrayList<>();
        if (newQuotes != null && !newQuotes.isEmpty()) {

            ArrayList<QuoteViewModel> newViewModels = newQuotes.stream()
                    .map(q -> new QuoteViewModel(q, R.mipmap.icon_foreground))
                    .collect(Collectors.toCollection(ArrayList::new));
            _adapter.addQuotes(newViewModels);

            if(numberItemsToWholePage <= newQuotes.size())
                currentPage++;
        }

        isLoading = false;
    }

    private void CheckUserConnection(){
        if(!_service.IsConnection()){
            new AlertDialog.Builder(getContext())
                    .setTitle("Exception access to Database")
                    .setMessage("Cannot use database, no connection!")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}

