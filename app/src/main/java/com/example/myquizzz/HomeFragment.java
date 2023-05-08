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

import java.lang.reflect.Array;


public class HomeFragment extends Fragment {
    View fragmentView;
    Button Art,History,Science,Music,Geography;
    private ViewItemModel viewModel;
    String[] Data = new String[2];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewItemModel.class);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data[0]="Field";


        Art = fragmentView.findViewById(R.id.Art);
        History = fragmentView.findViewById(R.id.History);
        Science = fragmentView.findViewById(R.id.Science);
        Music = fragmentView.findViewById(R.id.Music);
        Geography = fragmentView.findViewById(R.id.Geography);

        Art.setOnClickListener(fragmentView -> getField("Art"));
        History.setOnClickListener(fragmentView -> getField("History"));
        Science.setOnClickListener(fragmentView -> getField("Science"));
        Music.setOnClickListener(fragmentView -> getField("Music"));
        Geography.setOnClickListener(fragmentView -> getField("Geography"));
    }
    public void getField(String string){
        System.out.println("this has been clicked");
        switch (string) {
            case "Geography":
                Data[1] = "Địa Lý";
                break;
            case "History":
                Data[1] = "Lịch Sử";
                break;
            case "Art":
                Data[1] = "Nghệ Thuật";
                break;
            case "Music":
                Data[1] = "Âm nhạc";
                break;
            case "Science":
                Data[1] = "Khoa học";
                break;
            default:
                break;
        }
        viewModel.setData(Data);
    }
}
