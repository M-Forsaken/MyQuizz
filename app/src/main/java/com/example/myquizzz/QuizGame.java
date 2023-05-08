package com.example.myquizzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuizGame extends AppCompatActivity {
    private int currentApiVersion;
    HistoryModel Current;
    private ArrayList<HistoryModel> HistoryList = new ArrayList<>();
    String Status = "Home",previous_play;
    Button HistoryFragment,HomeFragment;
    Fragment Home,PlayHistory,Difficulty,InGame,Result;
    private ViewItemModel viewModel;
    String[][] Data = new String[4][2];
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
    String Play_date;
    int NumQuestion = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        Home = new HomeFragment();
        Difficulty = new DifficultyFragment();

        Data[0][0] = "Field";
        Data[1][0] = "Difficulty";
        Data[2][0] = "Correct_ans";
        Data[3][0] = "Play_date";



        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        replaceFragment(Home);

        final int flags =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        HistoryFragment = findViewById(R.id.HistoryFragment);
        HomeFragment = findViewById(R.id.HomeFragment);


        HistoryFragment.setOnClickListener(view ->  {
            Status = "History";
            PlayHistory = new HistoryFragment(HistoryList);
            replaceFragment(PlayHistory);
        });
        HomeFragment.setOnClickListener(view ->  {
            if (!Status.equals("Home")) {
                Status = "Home";
                replaceFragment(Home);
            }
        });
        viewModel = new ViewModelProvider(this).get(ViewItemModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            switch(item[0]){
                case "Field":
                    Data[0][1] = item[1];
                    replaceFragment(Difficulty);
                    break;
                case "Difficulty":
                    Data[1][1] = item[1];
                    Play_date = sdf.format(new Date());
                    Data[3][1] = Play_date;
                    InGame = new IngameFragment(Data[0][1],Data[1][1]);
                    replaceFragment(InGame);
                    break;
                case "Correct_ans":
                    Data[2][1] = item[1];
                    Result = new ResultFragment(Integer.parseInt(Data[2][1]));
                    Data[2][1] = Data[2][1] + "/" + NumQuestion;
                    updateHistory();
                    replaceFragment(Result);
                    break;
                case "Continue":
                    if( item[1] == "Home")
                        replaceFragment(Home);
                    else if (item[1] == "Share")
                        ShareResult();

                default:
                    break;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.About_me:
                startActivity(new Intent(QuizGame.this,About.class));
                return true;
            case R.id.Contact:
                sendMail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }


    private void sendMail() {
        String[] To_emails = {"20021554@vnu.edu.vn"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, To_emails);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Góp ý. Phiên bản Android: " + currentApiVersion);
        startActivity(Intent.createChooser(intent,"Choose an application"));
    }
    private void ShareResult() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, String.format("Tôi đã trả lời đúng %s câu hỏi cấp độ %s ở chủ đề %s.",Data[2][1],Data[1][1],Data[0][1]));
        intent.setType("text/italic");

        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(Intent.createChooser(intent,"Share"));
        }
    }
    private void updateHistory(){
        Current = new HistoryModel();
        Current.Field = Data[0][1];
        Current.Difficulty = Data[1][1];
        Current.Correct_ans = Data[2][1];
        Current.Play_Date = Data[3][1];
        if (previous_play != Current.Play_Date) {
            HistoryList.add(Current);
            previous_play = Current.Play_Date;
        }
    }
}