package com.example.traineesofveres.Application.UI.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traineesofveres.Application.UI.MainActivity;
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

        FillProfile();

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

    private void FillProfile(){
        if (_account != null) {
            _editTextName.setText(_account.Name);
            _editTextSurname.setText(_account.Surname);
            _editTextEmail.setText(_account.Email);
            _editTextAge.setText(Integer.toString(_account.Age));
            _textViewScore.setText(Integer.toString(_account.Score));
        }
    }

    private void SetBehaviorSaveButton(){
        _saveButton.setOnClickListener(view -> UpdateProfile());
    }

    private void SetBehaviorExitButton(){
        _exitButtom.setOnClickListener(view -> {
            CleanUserPreferences();

            new AlertDialog.Builder(getContext())
                    .setTitle("\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D")
                    .setMessage("Are you really wanting leave Mr. Veres\uD83E\uDD79?")
                    .setPositiveButton("Yes\uD83E\uDD72", (dialog, which) -> {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("No\uD83D\uDE0A\uD83D\uDE0A\uD83D\uDC68\u200D\uD83D\uDCBB\uD83D\uDC69\u200D\uD83D\uDCBB\uD83E\uDD73", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }

    private void UpdateProfile(){

        try {
            _service.UpdateTrainee(GetUpdatedTrainee());
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }

    private TraineeModel GetUpdatedTrainee(){
        TraineeModel updatedTrainee = new TraineeModel();

        updatedTrainee.Id = _account.Id;
        updatedTrainee.Name = _editTextName.getText().toString().trim();
        updatedTrainee.Surname = _editTextSurname.getText().toString().trim();
        updatedTrainee.Email = _editTextEmail.getText().toString().trim();
        updatedTrainee.Password = _account.Password;
        String updatedAgeStr = _editTextAge.getText().toString().trim();
        updatedTrainee.Score = _account.Score;

        try {
            updatedTrainee.Age = Integer.parseInt(updatedAgeStr);
        } catch (NumberFormatException e) {
            updatedTrainee.Age = _account.Age;
        }

        return updatedTrainee;
    }

    private  void CleanUserPreferences(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.UserPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}