package com.example.credittask;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView NameTextView, ScoreTextView;
    private int score,totalQues;
    private String name;
    private Button RestartBTN, FinishBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        NameTextView = findViewById(R.id.nameTV);
        ScoreTextView = findViewById(R.id.scoreTV);
        RestartBTN = findViewById(R.id.restartBTN);
        FinishBTN = findViewById(R.id.finishBTN);

        score = getIntent().getIntExtra("score",0);
        totalQues = getIntent().getIntExtra("totalQues",0);
        name  = getIntent().getStringExtra("name");

        NameTextView.setText("Congratulations "+ name);
        ScoreTextView.setText(String.valueOf(score + "/"+ totalQues));

        FinishBTN.setOnClickListener(V -> {
            finishAffinity();
        });

        RestartBTN.setOnClickListener(V -> {
            Intent QuizIntent = new Intent(ResultActivity.this,quizactivity.class);
            QuizIntent.putExtra("userName",name);
            startActivity(QuizIntent);
        });
    }
}