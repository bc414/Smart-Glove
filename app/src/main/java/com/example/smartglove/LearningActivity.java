package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }

    public void getNextLetter(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        position++;
        if(position == letterOrder.size()) {
            position = 0;
        }
        SignLetter letter = app.signLetters.get(letterOrder.get(position));
        imageView.setImageResource(letter.imageID);
    }

    public void testGlove(View view) {
        SignLetter letter = app.determineLetter();
    }

}