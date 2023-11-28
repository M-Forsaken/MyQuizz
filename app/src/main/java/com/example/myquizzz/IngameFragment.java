package com.example.myquizzz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngameFragment extends Fragment {
    View fragmentView;
    Random random = new Random();
    private ViewItemModel viewModel;
    Button True,False;
    TextView QuestionMessage,timeText,QuestionCount;
    ProgressBar QuestionProgress,timer;
    CountDownTimer countDownTimer;
    int timerValue = 20;
    int numCorrect = 0;
    int CurrentQuestion = 1;
    boolean isCounterRunning = false,repeated = false;
    String QuestionNum,Field,Difficulty;
    String[] Data = new String[2];
    int[] QuestionSequence = new int[5];
    private List<Question> Questions=new ArrayList<>();
    int temp = 0,counter = 0;

    public IngameFragment(String Field,String Difficulty){
        this.Field = Field;
        this.Difficulty = Difficulty;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_ingame, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewItemModel.class);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Questions=getQuestionList(Field,Difficulty);
        while (counter < 5) {
            temp = random.nextInt(Questions.size());
            System.out.print(temp + " ");
            for (int j = 0; j < counter; j++) {
                if (QuestionSequence[j] == temp) {
                    repeated = true;
                    break;
                }
            }
            if(repeated == true){
                repeated = false;
                continue;
            }
            QuestionSequence[counter] = temp;
            counter++;
        }
        timer = fragmentView.findViewById(R.id.Timer);
        True = fragmentView.findViewById(R.id.True);
        False = fragmentView.findViewById(R.id.False);
        QuestionProgress = fragmentView.findViewById(R.id.Question_progress);
        QuestionMessage = fragmentView.findViewById(R.id.Question);
        QuestionCount = fragmentView.findViewById(R.id.Question_Counter);
        timeText = fragmentView.findViewById(R.id.TimerText);
        Data[0] = "Correct_ans";


        True.setOnClickListener(fragmentView->getAnswer("True"));
        False.setOnClickListener(fragmentView->getAnswer("False"));

        countDownTimer = new CountDownTimer(timerValue*1000,1000) {
            @Override
            public void onTick(long l) {
                timer.setProgress(timerValue);
                timeText.setText(Integer.toString(timerValue));
                timerValue = timerValue - 1;
            }

            @Override
            public void onFinish() {
                QuestionProgress.setProgress((int)(CurrentQuestion/(1.0*QuestionSequence.length)*100));
                if (CurrentQuestion < QuestionSequence.length) {
                    CurrentQuestion++;
                    updateQuestion();
                }
                else {
                    Data[1] = Integer.toString(numCorrect);
                    viewModel.setData(Data);
                    CurrentQuestion = 1;
                    numCorrect = 0 ;
                    QuestionNum = CurrentQuestion + "/" +QuestionSequence.length;
                    QuestionCount.setText(QuestionNum);
                    QuestionProgress.setProgress(0);
                    countDownTimer.cancel();
                    isCounterRunning = false;
                    timerValue = 20;
                }
            }
        };

        updateQuestion();
    }
    public void getAnswer(String userAnswer){
        QuestionProgress.setProgress((int)(CurrentQuestion/(1.0*QuestionSequence.length)*100));
        if (Questions.get(QuestionSequence[CurrentQuestion - 1]).getQuestionAnswer() == Boolean.parseBoolean(userAnswer)) {
            numCorrect++;
        }
        if (CurrentQuestion < QuestionSequence.length) {
            CurrentQuestion++;
            updateQuestion();
        }
        else {
            Data[1] = Integer.toString(numCorrect);
            viewModel.setData(Data);
            CurrentQuestion = 1;
            numCorrect = 0 ;
            QuestionNum = CurrentQuestion + "/" +QuestionSequence.length;
            QuestionCount.setText(QuestionNum);
            QuestionProgress.setProgress(0);
            countDownTimer.cancel();
            isCounterRunning = false;
            timerValue = 20;
        }
    }
    public void updateQuestion(){
        QuestionNum = CurrentQuestion + "/" +QuestionSequence.length;
        QuestionCount.setText(QuestionNum);
        QuestionMessage.setText(Questions.get(QuestionSequence[CurrentQuestion - 1]).getQuestionText());
        if (!isCounterRunning) {
            countDownTimer.start();
            isCounterRunning = true;
        }
        else {
            countDownTimer.cancel();
            timerValue = 20;
            countDownTimer.start();
        }

    }
    public List getQuestionList(String Field, String Difficulty){
        List<Question> Questions=new ArrayList<>();
        String[] split;
        BufferedReader reader;
        try {

            InputStream file = getContext().getAssets().open("questions.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                if (line.contains("Field:"+Field)) {
                    line = reader.readLine();
                    break;
                }
                line = reader.readLine();
            }
            while(line!=null&&!line.contains("Difficulty:"+Difficulty))
                line = reader.readLine();
            line = reader.readLine();
            while(line != null&&!line.contains("Field:")&&!line.contains("Difficulty:")) {
                split = line.split(":");
                Questions.add(new Question(split[0],Boolean.parseBoolean(split[1])));
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return Questions;
    }

}