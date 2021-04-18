package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToCallibrate(View view) {
        Intent myIntent = new Intent(this,BluetoothActivity.class);
        startActivity(myIntent);
    }

    public void goToInterpret(View view) {
        Intent myIntent = new Intent(this,LearningActivity.class);
        myIntent.putExtra("mode","interpret");
        startActivity(myIntent);
    }

    public void goToLetterSelect(View view) {
        Intent myIntent = new Intent(this,GridviewAlph.class);
        startActivity(myIntent);
    }

    public void goToLearning(View view) {
        Intent myIntent = new Intent(this,LearningActivity.class);
        myIntent.putExtra("mode","learning");
        startActivity(myIntent);
    }

    public void goToQuizzes(View view) {
        Intent myIntent = new Intent(this,LearningActivity.class);
        myIntent.putExtra("mode","quizzes");
        startActivity(myIntent);
    }
}