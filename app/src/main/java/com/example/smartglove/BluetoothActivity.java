package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    SmartGloveApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        app = ((SmartGloveApplication)this.getApplication());

        Rect rect = new Rect(50, 50, 100, 100);


    }

    public void goToMenu(View view) {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void requestData(View view) {
        app.request();
    }

    public void connect(View view) {
        app.connectBluetooth();
    }

    public void test(View view) {

        SignLetter letter = app.determineLetter(app.readGlove());
        String text;
        if(letter == null) {
            text = "No match";
        }
        else {
            text = letter.getString();
        }

        TextView valueBox = findViewById(R.id.valueBox);
        valueBox.setText(text);
    }

    public void updateSideGyro(View view) {
        app.savedSideGyro = app.readGlove().palmGyro;
    }

    public void updateUpGyro(View view) {
        app.savedUpGyro = app.readGlove().palmGyro;
    }

    public void updateDownGyro(View view) {
        app.savedDownGyro = app.readGlove().palmGyro;
    }

}