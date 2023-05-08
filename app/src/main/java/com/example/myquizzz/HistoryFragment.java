package com.example.myquizzz;

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

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    TextView Message;
    private ArrayList<HistoryModel> HistoryList = new ArrayList<>();

    public HistoryFragment(ArrayList<HistoryModel> historyList) {
        HistoryList = historyList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Message = view.findViewById(R.id.HistoryMessage);
        recyclerView = view.findViewById(R.id.History_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(getContext(),HistoryList);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();

        if (HistoryList.size()==0)
            Message.setText("Bạn chưa chơi trận nào cả.");
        else Message.setText("");
    }
}