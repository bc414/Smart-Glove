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

    class LetterMapping {
        String letter;
        int thumb,pointer,middle,ring,pinkie;
        public LetterMapping(String letter, int thumb, int pointer, int middle, int ring, int pinkie) {
            this.letter = letter;
            this.thumb = thumb;
            this.pointer = pointer;
            this.middle = middle;
            this.ring = ring;
            this.pinkie = pinkie;
        }
    }

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public HashMap<String,SignLetter> signLetters;

    BluetoothSocket socket;

    FlexMapping thumb, pointer, middle, ring, pinkie;

    ArrayList<LetterMapping> letterMappings;

    public SmartGloveApplication() {
        super();
        signLetters = new HashMap<>();
        makeSignLetters();

        //define all thresholds here
        gestureSensorMapping();

    }

    private void gestureSensorMapping() {
        thumb = new FlexMapping();
        thumb.addThreshold(1,0,0);
        thumb.addThreshold(2,0,0);
        thumb.addThreshold(3,0,0);
        thumb.addThreshold(4,0,0);
        thumb.addThreshold(5,0,0);
        thumb.addThreshold(6,0,0);
        thumb.addThreshold(7,0,0);

        pointer = new FlexMapping();
        pointer.addThreshold(1,0,0);
        pointer.addThreshold(2,0,0);
        pointer.addThreshold(3,0,0);
        pointer.addThreshold(4,0,0);
        pointer.addThreshold(5,0,0);
        pointer.addThreshold(6,0,0);
        pointer.addThreshold(7,0,0);

        middle = new FlexMapping();
        middle.addThreshold(1,0,0);
        middle.addThreshold(2,0,0);
        middle.addThreshold(3,0,0);
        middle.addThreshold(4,0,0);
        middle.addThreshold(5,0,0);
        middle.addThreshold(6,0,0);
        middle.addThreshold(7,0,0);

        ring = new FlexMapping();
        ring.addThreshold(1,0,0);
        ring.addThreshold(2,0,0);
        ring.addThreshold(3,0,0);
        ring.addThreshold(4,0,0);
        ring.addThreshold(5,0,0);
        ring.addThreshold(6,0,0);
        ring.addThreshold(7,0,0);

        pinkie = new FlexMapping();
        pinkie.addThreshold(1,0,0);
        pinkie.addThreshold(2,0,0);
        pinkie.addThreshold(3,0,0);
        pinkie.addThreshold(4,0,0);
        pinkie.addThreshold(5,0,0);
        pinkie.addThreshold(6,0,0);
        pinkie.addThreshold(7,0,0);

        letterMappings = new ArrayList<>();
        letterMappings.add(new LetterMapping("A",1,6,6,6,6));
        letterMappings.add(new LetterMapping("B", 4, 1, 1, 1, 1));
        letterMappings.add(new LetterMapping("C", 2, 2, 2, 2, 2));
        letterMappings.add(new LetterMapping("D", 1, 1, 3, 3, 3));
        letterMappings.add(new LetterMapping("E", 4, 4, 4, 4, 4));
        letterMappings.add(new LetterMapping("F",2,3,1,1,1));
        letterMappings.add(new LetterMapping("G",1,1,4,4,4));
        letterMappings.add(new LetterMapping("H",1,1,1,4,4));
        letterMappings.add(new LetterMapping("I",2,6,6,6,1));
        letterMappings.add(new LetterMapping("J",2,6,6,6,1));
        letterMappings.add(new LetterMapping("K",1,1,1,6,5));
        letterMappings.add(new LetterMapping("L",1,1,6,6,6));
        letterMappings.add(new LetterMapping("M",4,5,5,5,5));
        letterMappings.add(new LetterMapping("N",5,5,5,6,6));
        letterMappings.add(new LetterMapping("O",5,5,5,5,5));
        letterMappings.add(new LetterMapping("P",1,1,1,6,5));
        letterMappings.add(new LetterMapping("Q",1,1,5,5,5));
        letterMappings.add(new LetterMapping("R",1,1,5,5,7));
        letterMappings.add(new LetterMapping("S",4,4,4,4,4));
        letterMappings.add(new LetterMapping("T",2,2,6,6,6));
        letterMappings.add(new LetterMapping("U",4,1,1,4,4));
        letterMappings.add(new LetterMapping("V",4,1,1,4,4));
        letterMappings.add(new LetterMapping("W",4,1,1,1,4));
        letterMappings.add(new LetterMapping("X",4,7,4,4,4));
        letterMappings.add(new LetterMapping("Y",1,6,6,6,1));
        letterMappings.add(new LetterMapping("Z",4,1,4,4,4));
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

        //System.out.println(bluetoothAdapter.getBondedDevices());
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

    public void request() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(22);
        }
        catch(IOException e) {
            System.out.println("REQUEST DIDN'T WORK!!!!!");
        }
    }

    public GloveReading readGlove() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();

            StringBuilder result = new StringBuilder();
            System.out.println("NUMBER OF BYTES AVAILABLE: " + inputStream.available());
            while(inputStream.available() > 0) {
                result.append((char) inputStream.read());
            }
            String data = result.toString();
            System.out.println("THE STRING FROM THE HC05: " + data);


            String[] lines = data.split(" ");
            double[] values = new double[lines.length];
            for(int i = 0; i < values.length; i++) {
                values[i] = Double.parseDouble(lines[i]);
            }

            //return null;
            return new GloveReading(new Flex(values[0],values[1],values[2],values[3],values[4]), new Accel(values[5],values[6],values[7]), new Accel(values[8],values[9],values[10]), new Gyro(values[11],values[12],values[13]), new Gyro(values[14],values[15],values[17]));
        }
        catch(IOException e) {
            System.out.println("READ GLOVE DIDN'T WORK!!!!!!!!!!!!!!");
        }
        return null;
    }

    public SignLetter determineLetter() {
        GloveReading glove = readGlove();
        int thumbRegion = thumb.evaluate(glove.flex.thumb);
        int pointerRegion = pointer.evaluate(glove.flex.pointer);
        int middleRegion = middle.evaluate(glove.flex.middle);
        int ringRegion = ring.evaluate(glove.flex.ring);
        int pinkieRegion = pinkie.evaluate(glove.flex.pinkie);

        for(LetterMapping letterMapping : letterMappings) {
            if(letterMapping.thumb == thumbRegion && letterMapping.pointer == pointerRegion && letterMapping.middle == middleRegion && letterMapping.ring == ringRegion && letterMapping.pinkie == pinkieRegion) {
                return signLetters.get(letterMapping.letter);
            }
        }

        return null;
    }

}
