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

import com.example.traineesofveres.R;
import com.example.traineesofveres.Application.UI.login.LoginActivity;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private Button _exitButtom, _saveButton;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        _saveButton = view.findViewById(R.id.profile_saveButton);
        _exitButtom = view.findViewById(R.id.profile_exitButton);

        SetBehaviorSaveButton();
        SetBehaviorExitButton();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
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