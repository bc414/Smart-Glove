package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class LearningActivity extends AppCompatActivity {

    SmartGloveApplication app;
    ArrayList<String> letterOrder;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        app = (SmartGloveApplication)(this.getApplication());
        System.out.println(app);

        letterOrder = new ArrayList<>(app.signLetters.keySet());
        Collections.shuffle(letterOrder);
        position = 0;

        String mode = getIntent().getStringExtra("mode");

        if(mode.equals("learning")) {

        }
        else if(mode.equals("quizzes")) {

        }
        else if(mode.equals("interpret")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    interpret();
                    handler.postDelayed(this, 2000);
                }
            }, 2000);  //the time is in miliseconds
        }

        //testing purposes only
        /*ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new Click("A"));*/

    }

    /*
    class Click implements View.OnClickListener {

        String letter;

        public Click(String letter) {
            this.letter = letter;
        }

        public void onClick(View v) {
            goToLearning(letter);
        }
    }

    public void goToLearning(String letter) {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("letter", letter);
        startActivity(myIntent);
    }*/

    public void getNextLetter(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        position++;
        if(position == letterOrder.size()) {
            position = 0;
        }
        SignLetter letter = app.signLetters.get(letterOrder.get(position));
        imageView.setImageResource(letter.imageID);
    }

    public void goToMenu(View view) {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void interpret() {
        System.out.println("INTERPRETING!");
        ImageView imageView = findViewById(R.id.imageView);
        SignLetter letter = app.determineLetter();
        imageView.setImageResource(letter.imageID);
    }

    public void testGlove(View view) {
        SignLetter letter = app.determineLetter();
    }

}