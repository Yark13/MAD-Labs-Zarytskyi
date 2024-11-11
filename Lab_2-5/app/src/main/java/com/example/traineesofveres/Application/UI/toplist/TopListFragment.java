package com.example.traineesofveres.Application.UI.toplist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.R;
import com.example.traineesofveres.DTO.Aplication.TraineeViewModel;
import com.example.traineesofveres.Application.UI.adapters.TraineesAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopListFragment extends Fragment {

    @Inject
    ITraineeService _service;

    private ArrayList<TraineeViewModel> _traineesModel = new ArrayList<TraineeViewModel>();
    private TopListViewModel mViewModel;

    public static TopListFragment newInstance() {
        return new TopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.TraineesRecyclerView);

        SetUpTraineesModels();

        TraineesAdapter adapter = new TraineesAdapter(getContext(), _traineesModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void SetUpTraineesModels(){
        _traineesModel.clear();
        String[] names = getResources().getStringArray(R.array.names);
        String[] surnames = getResources().getStringArray(R.array.surnames);
        String[] ages = getResources().getStringArray(R.array.ages);
        String[] scores = getResources().getStringArray(R.array.scores);

        for(int i = 0; i < names.length; i++){
            try {
                _traineesModel.add(new TraineeViewModel(i, names[i], surnames[i], Integer.parseInt(ages[i]), Integer.parseInt(scores[i])));
            }
            catch (NumberFormatException e) {

                System.out.println("Invalid String for TraineeViewModel age or score");
            }
        }

    }
}