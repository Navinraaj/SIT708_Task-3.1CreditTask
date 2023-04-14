package com.example.credittask;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class quizactivity extends AppCompatActivity implements View.OnClickListener{

    private TextView test;
    private TextView textViewRemQ;
    private TextView textViewQuestion;
    private ProgressBar progressBar;
    private Button[] answerBtn = new Button[4];
    private int Qindex = 0;
    private int correctAns = 0;
    private String userName;
    private List<Question> questions;
    private Button submitBTN;
    private String selectedAnswer = "";
    private  Button ansA, ansB, ansC, ansD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        Intent startIntent = getIntent();
        userName = startIntent.getStringExtra("userName");
        progressBar = findViewById(R.id.progressBar);
        textViewRemQ = findViewById(R.id.questionCount);
        answerBtn[0] = ansA = findViewById(R.id.ans1);
        answerBtn[1] = ansB = findViewById(R.id.ans2);
        answerBtn[2] = ansC = findViewById(R.id.ans3);
        answerBtn[3] = ansD = findViewById(R.id.ans4);
        submitBTN    = findViewById(R.id.submitBtn);
        questions = loadQuestions();
        showQuestions();
        textViewRemQ.setText("1/"+questions.size());
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBTN.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submitBtn) {
            if(Qindex < questions.size() - 1) {
                Qindex++;
                showQuestions();
            } else {
                Intent intent = new Intent(quizactivity.this,ResultActivity.class);
                intent.putExtra("score",correctAns);
                intent.putExtra("name",userName);
                intent.putExtra("totalQues",questions.size());
                startActivity(intent);
                finish();
            }
        } else {

            if (clickedButton.getText().toString() == questions.get(Qindex).getCorrectAnsIndex()) {
                checkAns(true);
                clickedButton.setBackgroundResource(android.R.color.holo_green_light);
            } else {
                checkAns(false);
                for (int i = 0; i < answerBtn.length; i++) {
                    if (answerBtn[i].getText().toString() == questions.get(Qindex).getCorrectAnsIndex()) {
                        answerBtn[i].setBackgroundResource(android.R.color.holo_green_light);
                        clickedButton.setBackgroundResource(android.R.color.holo_red_light);
                    }
                }
            }
        }
        textViewRemQ.setText(Qindex+"/"+questions.size());
    }
    private void showQuestions() {
        Question question = questions.get(Qindex);
        textViewQuestion.setText(question.getQuestion());
        for (int i = 0; i < answerBtn.length; i++){
            answerBtn[i].setText(question.getOptions().get(i));
            answerBtn[i].setBackgroundResource(android.R.drawable.btn_default);
            answerBtn[i].setEnabled(true);
        }
        progressBar.setProgress((int) (((float) Qindex / (float) questions.size()) * 100));
    }

    private void checkAns(boolean status) {
        for (int i = 0; i < answerBtn.length; i++) {
            answerBtn[i].setEnabled(false);
        }
        if(status) {
            correctAns++;
        }
    }
    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("University Name", Arrays.asList("Deakin","MIT","UTS","RMIT"),"Deakin"));
        questions.add(new Question("Class name", Arrays.asList("SIT708","SIT453","SIT345","SIT678"),"SIT708"));
        questions.add(new Question("Campus", Arrays.asList("Burwood","Perth","Sydney","Brisbane"),"Burwood"));
        return questions;
    }
}

class Question{
    private String question;
    private List<String> options;
    private String correctAns;
    public Question(String question,List<String> options,String correctAns){
        this.question = question;
        this.options = options;
        this.correctAns = correctAns;
    }

    public String getQuestion(){return question;}

    public List<String> getOptions() {return options;}
    public String getCorrectAnsIndex() {return correctAns;}
}