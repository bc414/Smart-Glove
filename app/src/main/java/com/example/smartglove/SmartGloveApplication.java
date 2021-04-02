package com.example.smartglove;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SmartGloveApplication extends Application {

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public HashMap<String,SignLetter> signLetters;

    BluetoothSocket socket;

    public SmartGloveApplication() {
        super();
        signLetters = new HashMap<>();
        makeSignLetters();
    }

    private void makeSignLetters() {
        ArrayList<Integer> imageIDs = new ArrayList<>();
        imageIDs.add(R.drawable.a);
        imageIDs.add(R.drawable.b);

        System.out.println(imageIDs);

        for(int i = 0; i < imageIDs.size(); i++) {
            int id = 65+i;
            String key = Character.toString((char)id);
            signLetters.put(key, new SignLetter(id,imageIDs.get(i)));
        }
    }

    public void connectBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(bluetoothAdapter.getBondedDevices());
        BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice("hard code address");

        //socket code
        socket = null;
        try {
            socket = hc05.createRfcommSocketToServiceRecord(mUUID);
            socket.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GloveReading readGlove() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            outputStream.write(22);
            StringBuilder result = new StringBuilder();
            while(inputStream.available() > 0) {
                result.append((char) inputStream.read());
            }
            String data = result.toString();
            String[] lines = data.split(" ");
            double[] values = new double[lines.length];
            for(int i = 0; i < values.length; i++) {
                values[i] = Double.parseDouble(lines[i]);
            }

            return new GloveReading(new Flex(values[0],values[1],values[2],values[3],values[4]), new Accel(values[5],values[6],values[7]), new Accel(values[8],values[9],values[10]), new Gyro(values[11],values[12],values[13]), new Gyro(values[14],values[15],values[17]));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SignLetter determineLetter() {
        GloveReading glove = readGlove();
        return null;
    }

}
