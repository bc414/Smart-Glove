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

    public void setErrorMessage() {
        TextView valueBox = findViewById(R.id.valueBox);
        valueBox.setText("You need to connect first.");
    }

    public void requestData(View view) {
        try {
            app.request();
        }
        catch(Exception e) {
            setErrorMessage();
        }
    }

    public void connect(View view) {

        app.connectBluetooth();
        if(app.socket == null) {
            TextView valueBox = findViewById(R.id.valueBox);
            valueBox.setText("Socket didn't work!");
        }
        else {
            TextView valueBox = findViewById(R.id.valueBox);
            valueBox.setText("Socket works!");
        }

    }

    public void test(View view) {

        try {
            if(app.readGlove().flex == null) {
                TextView valueBox = findViewById(R.id.valueBox);
                valueBox.setText("You need to request data first.");
                return;
            }
            SignLetter letter = app.determineLetter();
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
        catch (NullPointerException e) {
            setErrorMessage();
        }

    }

    public void updateSideGyro(View view) {
        try {
            app.savedSideGyro = app.readGlove().palmGyro;
        }
        catch(Exception e) {
            setErrorMessage();
        }
    }

    public void updateUpGyro(View view) {
        try {
            app.savedUpGyro = app.readGlove().palmGyro;
        }
        catch(Exception e) {
            setErrorMessage();
        }
    }

    public void updateDownGyro(View view) {
        try {
            app.savedDownGyro = app.readGlove().palmGyro;
        }
        catch(Exception e) {
            setErrorMessage();
        }
    }

}