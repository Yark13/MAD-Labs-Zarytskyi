package com.example.traineesofveres.Application.UI.toplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.R;
import com.example.traineesofveres.DTO.Aplication.TraineeViewModel;
import com.example.traineesofveres.Application.UI.adapters.TraineesAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopListFragment extends Fragment {

    @Inject
    public ITraineeService _service;

    private static final String ARG_TRAINEE_ACCOUNT = "ARG_TRAINEE_ACCOUNT";
    private static final int COUNT_OF_TOP_TRAINEE = 10;

    private ArrayList<TraineeViewModel> _traineesModel = new ArrayList<TraineeViewModel>();

    private RecyclerView _recyclerView;
    private TextView _viewerUserPosition;

    private TraineeModel _trainee;
    private TopListViewModel mViewModel;

    public static TopListFragment newInstance(int traineeId) {
        TopListFragment fragment = new TopListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRAINEE_ACCOUNT, traineeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int traineeId = (int) getArguments().getSerializable(ARG_TRAINEE_ACCOUNT);
            _trainee = _service.Find (traineeId);
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FindingViewElements(view);

        CreateTop();
    }

    private void FindingViewElements(View view){
        _recyclerView = view.findViewById(R.id.TraineesRecyclerView);
        _viewerUserPosition = view.findViewById(R.id.topList_viewerUserPosition);
    }

    private void CreateTop(){

        SetUpTraineesModels();


        TraineesAdapter adapter = new TraineesAdapter(getContext(), new ArrayList<>(_traineesModel.subList(0, _traineesModel.size()-1)));
        _recyclerView.setAdapter(adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void SetUpRecyclerView(){

    }

    private void SetUpTraineesModels(){
        _traineesModel.clear();

        try {
            _traineesModel = _service.GetTop(COUNT_OF_TOP_TRAINEE, _trainee.Id).stream()
                    .map(TraineeViewModel::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }
}