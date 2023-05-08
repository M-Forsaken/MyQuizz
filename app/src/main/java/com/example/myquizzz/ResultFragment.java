package com.example.myquizzz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultFragment extends Fragment {
    View fragmentView;
    private ViewItemModel viewModel;
    Button restart,share;
    ProgressBar scoreBar;
    String[] Data = new String[2];
    int score;
    TextView ScoreCount;
    public ResultFragment(int Score){
        this.score = Score;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_result, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewItemModel.class);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data[0] = "Continue";

        scoreBar = fragmentView.findViewById(R.id.scoreBar);
        restart = fragmentView.findViewById(R.id.Restart);
        share = fragmentView.findViewById(R.id.Share);
        ScoreCount = fragmentView.findViewById(R.id.Score);

        ScoreCount.setText(score+"/5");
        scoreBar.setProgress((int)(score/(5*1.0)*100));

        restart.setOnClickListener(fragmentView->getAction("Home"));
        share.setOnClickListener(fragmentView->getAction("Share"));
    }
    public void getAction(String string){
        switch (string){
            case "Home":
                Data[1] = "Home";
                break;
            case "Share":
                Data[1] = "Share";
                break;
        }
        viewModel.setData(Data);
    }

}