package com.example.traineesofveres.ui.trainee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traineesofveres.DTO.Aplication.TraineeViewModel;
import com.example.traineesofveres.R;

import java.util.ArrayList;

public class TraineesAdapter extends RecyclerView.Adapter<TraineesAdapter.TraineeViewHolder>{
    Context _context;
    ArrayList<TraineeViewModel> _traineeViewModels;

    public TraineesAdapter(Context _context, ArrayList<TraineeViewModel> _traineeViewModels) {
        this._context = _context;
        this._traineeViewModels = _traineeViewModels;
    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.trainees_recycler_view_row, parent, false);
        return new TraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, int position) {
        holder.holder.setCardBackgroundColor(GetColor(position));
        holder.topPlace.setText(Integer.toString(position+1));
        holder.name.setText(_traineeViewModels.get(position).getName());
        holder.surname.setText(_traineeViewModels.get(position).getSurname());
        holder.age.setText(Integer.toString(_traineeViewModels.get(position).getAge()));
        holder.score.setText(Integer.toString(_traineeViewModels.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return _traineeViewModels.size();
    }

    public  static  class TraineeViewHolder extends  RecyclerView.ViewHolder{
        TextView topPlace, name, surname, age, score;
        CardView holder;

        public TraineeViewHolder(@NonNull View itemView) {
            super(itemView);
            topPlace = itemView.findViewById(R.id.traineesRecyclerViewRow_textView_topPlace);
            name = itemView.findViewById(R.id.traineesRecyclerViewRow_textView_name);
            surname = itemView.findViewById(R.id.traineesRecyclerViewRow_textView_surname);
            age = itemView.findViewById(R.id.traineesRecyclerViewRow_textView_age);
            score = itemView.findViewById(R.id.traineesRecyclerViewRow_textView_score);
            holder = itemView.findViewById(R.id.trainees_CardView);
        }
    }

    private int GetColor(int Position){
        int color = ContextCompat.getColor(_context, R.color.white);
        switch (Position){
            case 0:
                color = ContextCompat.getColor(_context, R.color.gold);
                break;
            case 1:
                color = ContextCompat.getColor(_context, R.color.silver);
                break;
            case 2:
                color = ContextCompat.getColor(_context, R.color.copper);
                break;
            case 3: case 4:
                color = ContextCompat.getColor(_context, R.color.top5Color);
                break;
            default:
                break;
        }

        return color;
    }
}

