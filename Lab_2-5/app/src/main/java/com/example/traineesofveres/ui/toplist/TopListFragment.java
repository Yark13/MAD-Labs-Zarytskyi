package com.example.traineesofveres.ui.toplist;

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

import com.example.traineesofveres.R;
import com.example.traineesofveres.DTO.Aplication.TraineeViewModel;
import com.example.traineesofveres.ui.trainee.TraineesAdapter;

import java.util.ArrayList;

public class TopListFragment extends Fragment {

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
        // TODO: Use the ViewModel
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