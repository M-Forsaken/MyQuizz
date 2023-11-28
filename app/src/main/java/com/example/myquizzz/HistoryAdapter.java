package com.example.myquizzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<HistoryModel> HistoryList;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyList) {
        this.context = context;
        this.HistoryList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel historyModel = HistoryList.get(position);
        holder.Field_history.setText(historyModel.Field);
        holder.Difficulty_history.setText(historyModel.Difficulty);
        holder.correct_ans.setText(historyModel.Correct_ans);
        holder.Play_date.setText(historyModel.Play_Date);
    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Field_history,Difficulty_history,Play_date,correct_ans;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Field_history = itemView.findViewById(R.id.Field_history_2);
            Difficulty_history = itemView.findViewById(R.id.Difficulty_history_2);
            Play_date = itemView.findViewById(R.id.Date_2);
            correct_ans = itemView.findViewById(R.id.Correct_Answer_2);
        }
    }
}
