package com.example.traineesofveres.Application.UI.taplike;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TapLikeFragment extends Fragment {

    @Inject
    ITraineeService _service;

    private static final String ARG_TRAINEE_ACCOUNT = "ARG_TRAINEE_ACCOUNT";
    private TraineeModel _account;

    private Button _tapLikeButton;
    private TextView _scoreTextView;

    private int _numberOfLikeTaps = 0;
    private final int _frequentlySaveInDbCounterInLikes = 5;

    public static TapLikeFragment newInstance(int traineeId) {
        TapLikeFragment fragment = new TapLikeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRAINEE_ACCOUNT, traineeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int accountId = (int) getArguments().getSerializable(ARG_TRAINEE_ACCOUNT);
            _account = _service.Find(accountId);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tap_like, container, false);

        FindingViewElements(view);

        _scoreTextView.setText(Integer.toString(_account.Score));

        SetBehaviorTapLikeButton();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        _numberOfLikeTaps = 0;
        _service.UpdateTrainee(_account);
    }

    private void FindingViewElements(View view){
        _tapLikeButton = view.findViewById(R.id.tapLikeFragment_tapLikeButton);
        _scoreTextView = view.findViewById(R.id.tapLikeFragment_textView_numberLikes);
    }

    private void SetBehaviorTapLikeButton(){
        _tapLikeButton.setOnClickListener(view -> {
            _numberOfLikeTaps++;
            _account.Score++;
            _scoreTextView.setText(Integer.toString(_account.Score));

            if(_numberOfLikeTaps == _frequentlySaveInDbCounterInLikes){
                _numberOfLikeTaps = 0;
                _service.UpdateTrainee(_account);
            }
        });
    }
}