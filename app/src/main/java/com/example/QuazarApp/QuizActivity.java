package com.example.QuazarApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView mScoreView;
    private TextView mQuestionView;
    private List<Button> choiceButtons;
    private List<QuestionData> questions;
    private int currentQuestionIndex;
    private QuestionData currentQuestion;
    private int score;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions = new ArrayList<>();
        questions.add(new QuestionData(
                "This is owned by two different countries",
                new String[]{"everest", "new york", "yellow stone","Eiffel Tower"},
                R.drawable.everest));
        questions.add(new QuestionData(
                "More than 8 million people live here",
                new String[] {"new york","pyramids", "everest", "Eiffel Tower"},
                R.drawable.newyork));
        questions.add(new QuestionData(
                "Contains over 300 water falls",
                new String[] {"yellow stone", "everest", "Mount vesuvius","Eiffel Tower" },
                R.drawable.yellowstone));
        questions.add(new QuestionData(
                "It took 22 thousand worker to build",
                new String[] {"Taj Mahal", "Mount vesuvius", "Eiffel Tower", "everest"},
                R.drawable.taj));
        questions.add(new QuestionData(
                "Worlds most popular tourist destination",
                new String[] {"france", "Machu Picchu", "everest", "Eiffel Tower"},
                R.drawable.france));
        questions.add(new QuestionData(
                "4203 feet tall",
                new String[] { "Mount vesuvius", "pyramids","everest", "Eiffel Tower"},
                R.drawable.vesuvius));
        questions.add(new QuestionData(
                "This was discovered in 1911",
                new String[] {"Machu Picchu","Taj Mahal" , "everest", "Eiffel Tower"},
                R.drawable.machu));
        questions.add(new QuestionData(
                "Aligned with the Orion's belt constellation",
                new String[] {"pyramids", "everest", "Taj Mahal", "Eiffel Tower"},
                R.drawable.pyramids));

        startQuiz();
    }

    private void startQuiz(){
        setContentView(R.layout.quiz_main);

//        backButton =  (Button) findViewById(R.id.back);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {   //code within bracket is executed when button is presssed
//                setContentView(R.layout.activity_main);  //method is called
//            }
//        });

        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        choiceButtons = new ArrayList<>();
        choiceButtons.add(findViewById(R.id.choice1));
        choiceButtons.add(findViewById(R.id.choice2));
        choiceButtons.add(findViewById(R.id.choice3));
        choiceButtons.add(findViewById(R.id.choice4));

        View.OnClickListener clickListener = (View.OnClickListener) view ->{
            int choiceIndex = choiceButtons.indexOf(view);
            if(currentQuestion.getChoices().get(choiceIndex).isCorrect()){
                incrementScore();
                Toast.makeText(com.example.QuazarApp.QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(com.example.QuazarApp.QuizActivity.this, "Wrong! idiot", Toast.LENGTH_SHORT).show();
            }
            updateQuestion();
        };


        for(Button button : choiceButtons){
            button.setOnClickListener(clickListener);
        }
        currentQuestionIndex =0;
        Collections.shuffle(questions);
        updateQuestion();
        clearScore();
    }
    private void finishQuiz(){
        setContentView(R.layout.game_over);
        findViewById(R.id.restart).setOnClickListener((View.OnClickListener) view ->{
            setContentView(R.layout.quiz_main);
            startQuiz();
        });
        findViewById(R.id.back).setOnClickListener((View.OnClickListener) view ->{
            //setContentView(R.layout.activity_main);
            Intent intent = new Intent(this, MainActivity.class);  //Intent - the description of the activity to start
            startActivity(intent);
        });
        ((TextView) findViewById(R.id.endScore)).setText(Integer.toString(score));
    }
    private void updateQuestion(){
        if(currentQuestionIndex == questions.size()){
            finishQuiz();
            return;
        }
        currentQuestion = questions.get(currentQuestionIndex);
        currentQuestionIndex++;

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(currentQuestion.getImage());

        Collections.shuffle(currentQuestion.getChoices());
        for(int i=0; i<currentQuestion.getChoices().size(); i++){
            choiceButtons.get(i).setText(currentQuestion.getChoices().get(i).getChoiceText());
        }
        mQuestionView.setText(currentQuestion.getQuestion());
    }
    private void clearScore(){
        score = 0;
        mScoreView.setText(Integer.toString(score));
    }
    private void incrementScore() {
        score++;
        mScoreView.setText(Integer.toString(score));
    }
}