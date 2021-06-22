package com.example.QuazarApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;      //declaring button (creating objects for the views used)
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private ImageButton centerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button =  (ImageButton) findViewById(R.id.button);   //initialize button
        button2 = (ImageButton) findViewById(R.id.button2);
        button3 = (ImageButton) findViewById(R.id.button3);
        button4 = (ImageButton) findViewById(R.id.button4);


        centerButton = (ImageButton) findViewById(R.id.centerButton);

        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //code within bracket is executed when button is pressed
                openAPOD();            //method is called
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewsFeed();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //code within bracket is executed when button is pressed
                openQuizActivity();            //method is called
            }
        });
        button3.setOnClickListener(v -> { openRoverPhotosActivity("perseverance");});
        button4.setOnClickListener(v -> { openRoverPhotosActivity("curiosity");});
    }
    public void openAPOD() {
        Intent intent = new Intent(this, APOD.class);  //Intent - the description of the activity to start
        // used with startActivity to launch an Activity
        startActivity(intent);
    }
    public void openQuizActivity() {
        Intent intent = new Intent(this, QuizActivity.class);  //Intent - the description of the activity to start
        // used with startActivity to launch an Activity
        startActivity(intent);
    }
    public void openNewsFeed() {
        Intent intent = new Intent(this, NewsFeed.class);  //Intent - the description of the activity to start
        // used with startActivity to launch an Activity
        startActivity(intent);
    }
    public void openRoverPhotosActivity(String rover) {
        Intent intent = new Intent(this, RoverPhotosActivity.class);  //Intent - the description of the activity to start
        intent.putExtra("rover", rover);
        // used with startActivity to launch an Activity
        startActivity(intent);
    }
}