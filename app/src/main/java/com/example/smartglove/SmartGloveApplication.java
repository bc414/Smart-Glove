package com.example.smartglove;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

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
        BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice("98:D3:71:FD:F7:6A");

        ParcelUuid list[] = hc05.getUuids();

        UUID uuid = UUID.fromString(list[0].toString());
        System.out.println("UUID FROM THE HC05: " + uuid);

        System.out.println("HC05 OBJECT: " + hc05);
        //socket code
        socket = null;
        try {
            socket = hc05.createRfcommSocketToServiceRecord(uuid);
            System.out.println("SOCKET OBJECT: " + socket);
            socket.connect();
            System.out.println("SOCKET WORKS!!!!!!!!!!!!!!!!!!!");
        } catch (IOException e) {
            System.out.println("SOCKET DIDN'T WORK!!!!!!!!!!");
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
            System.out.println("NUMBER OF BYTES AVAILABLE: " + inputStream.available());
            while(inputStream.available() > 0) {
                result.append((char) inputStream.read());
            }
            String data = result.toString();
            System.out.println("THE STRING FROM THE HC05: " + data);

            /*
            String[] lines = data.split(" ");
            double[] values = new double[lines.length];
            for(int i = 0; i < values.length; i++) {
                values[i] = Double.parseDouble(lines[i]);
            }*/

            return null;
            //return new GloveReading(new Flex(values[0],values[1],values[2],values[3],values[4]), new Accel(values[5],values[6],values[7]), new Accel(values[8],values[9],values[10]), new Gyro(values[11],values[12],values[13]), new Gyro(values[14],values[15],values[17]));
        }
        catch(IOException e) {
            System.out.println("READ GLOVE DIDN'T WORK!!!!!!!!!!!!!!");
        }
        return null;
    }

    public SignLetter determineLetter() {
        GloveReading glove = readGlove();
        return null;
    }

}
