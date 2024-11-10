package com.example.traineesofveres.Application.UI.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.traineesofveres.DTO.Aplication.TraineeViewModel;
import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.R;
import com.example.traineesofveres.Application.UI.login.LoginActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    @Inject
    ITraineeService _service;

    private static final String ARG_TRAINEE_ACCOUNT = "ARG_TRAINEE_ACCOUNT";
    private TraineeModel _account;

    private Button _exitButtom, _saveButton;
    private TextView _textViewScore;
    private EditText _editTextName, _editTextSurname, _editTextAge, _editTextEmail;

    public static ProfileFragment newInstance(int traineeId) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FindingViewElements(view);



        if (_account != null) {
            _editTextName.setText(_account.Name);
            _editTextSurname.setText(_account.Surname);
            _editTextEmail.setText(_account.Email);
            _editTextAge.setText(Integer.toString(_account.Age));
            _textViewScore.setText(Integer.toString(_account.Score));
        }

        SetBehaviorSaveButton();
        SetBehaviorExitButton();

        return view;
    }

    private void FindingViewElements(View view){
        _editTextName = view.findViewById(R.id.profile_editTextName);
        _editTextSurname = view.findViewById(R.id.profile_editTextSurname);
        _editTextEmail = view.findViewById(R.id.profile_editTextEmail);
        _editTextAge = view.findViewById(R.id.profile_editTextAge);
        _textViewScore = view.findViewById(R.id.profile_textViewWithScore);

        _saveButton = view.findViewById(R.id.profile_saveButton);
        _exitButtom = view.findViewById(R.id.profile_exitButton);
    }

    private void SetBehaviorSaveButton(){

    }

    private void SetBehaviorExitButton(){
        _exitButtom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}