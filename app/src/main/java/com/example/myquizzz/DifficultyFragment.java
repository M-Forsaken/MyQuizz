package com.example.myquizzz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DifficultyFragment extends Fragment {
    View fragmentView;
    Button Easy,Medium,Hard;
    private ViewItemModel viewModel;
    String[] Data = new String[2];

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_difficulty, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewItemModel.class);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data[0]="Difficulty";


        Easy = fragmentView.findViewById(R.id.Easy);
        Medium = fragmentView.findViewById(R.id.Medium);
        Hard = fragmentView.findViewById(R.id.Hard);

        Easy.setOnClickListener(fragmentView->getDifficulty("Easy"));
        Medium.setOnClickListener(fragmentView->getDifficulty("Medium"));
        Hard.setOnClickListener(fragmentView->getDifficulty("Hard"));
    }
    public void getDifficulty(String string){
        System.out.println("this has been clicked");
        switch (string) {
            case "Easy":
                Data[1] = "Dễ";
                break;
            case "Medium":
                Data[1] = "Trung bình";
                break;
            case "Hard":
                Data[1] = "Khó";
                break;
            default:
                break;
        }
        viewModel.setData(Data);
    }
}