package com.example.traineesofveres.ui.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.traineesofveres.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder> {
    Context _context;
    ArrayList<QuoteViewModel> _quotesModels;

    public QuotesAdapter(Context context, ArrayList<QuoteViewModel> quotesModels){
        _context = context;
        _quotesModels = quotesModels;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.quotes_recycler_view_row, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        holder.quote.setText(_quotesModels.get(position).getQuote());
        holder.date.setText(_quotesModels.get(position).getDate());
        holder.imageView.setImageResource(_quotesModels.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return _quotesModels.size();
    }

    public  static  class QuoteViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView date, quote;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quotesRecyclerViewRow_imageView);
            date = itemView.findViewById(R.id.quotesRecyclerViewRow_textView_date);
            quote = itemView.findViewById(R.id.quotesRecyclerViewRow_textView_quote);
        }
    }
}
