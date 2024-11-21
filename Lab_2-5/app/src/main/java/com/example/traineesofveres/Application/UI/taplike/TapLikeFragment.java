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
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
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
    private KonfettiView _konfettiView;

    private int _counterNewLikeTaps = 0;
    private final int _frequentlySaveInDbCounterInLikes = 50;

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

        _counterNewLikeTaps = 0;
        _service.UpdateTrainee(_account);
    }

    private void FindingViewElements(View view){
        _tapLikeButton = view.findViewById(R.id.tapLikeFragment_tapLikeButton);
        _scoreTextView = view.findViewById(R.id.tapLikeFragment_textView_numberLikes);
        _konfettiView = view.findViewById(R.id.taplike_konfettiView);
    }

    private void SetBehaviorTapLikeButton(){
        _tapLikeButton.setOnClickListener(view -> {
            _counterNewLikeTaps++;
            _account.Score++;
            _scoreTextView.setText(Integer.toString(_account.Score));

            _konfettiView.build()
                    .addColors(0xFFF48FB1, 0xFF81DEEA, 0xFFFDD835)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                    .addSizes(new Size(12, 5f))
                    .setPosition(-50f, _konfettiView.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 5000L);

            if(_counterNewLikeTaps == _frequentlySaveInDbCounterInLikes){
                _counterNewLikeTaps = 0;
                _service.UpdateTrainee(_account);
            }
        });
    }
}