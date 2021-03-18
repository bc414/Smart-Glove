package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BluetoothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
    }

    public void goToMenu(View view) {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void updateValue(View view) {
        TextView valueBox = findViewById(R.id.valueBox);
        valueBox.setText("hello");
    }
}