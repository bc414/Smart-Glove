package com.example.smartglove;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
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

    }

    public void goToMenu(View view) {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void connect(View view) {
        app.connectBluetooth();
    }

    public void test(View view) {

        app.readGlove();

        TextView valueBox = findViewById(R.id.valueBox);
        valueBox.setText("hello");
    }


}