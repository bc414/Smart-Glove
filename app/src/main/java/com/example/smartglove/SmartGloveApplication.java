package com.example.smartglove;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SmartGloveApplication extends Application {

    class LetterMapping {
        String letter;
        int thumb,pointer,middle,ring,pinkie;
        String direction;
        public LetterMapping(String letter, int thumb, int pointer, int middle, int ring, int pinkie) {
            this.letter = letter;
            this.thumb = thumb;
            this.pointer = pointer;
            this.middle = middle;
            this.ring = ring;
            this.pinkie = pinkie;
            this.direction = "None";
        }
        public LetterMapping(String letter, int thumb, int pointer, int middle, int ring, int pinkie, String direction) {
            this.letter = letter;
            this.thumb = thumb;
            this.pointer = pointer;
            this.middle = middle;
            this.ring = ring;
            this.pinkie = pinkie;
            this.direction = direction;
        }
    }

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public HashMap<String,SignLetter> signLetters;

    BluetoothSocket socket;

    FlexMapping thumb, pointer, middle, ring, pinkie;

    ArrayList<LetterMapping> letterMappings;

    Gyro savedSideGyro, savedUpGyro, savedDownGyro;

    public SmartGloveApplication() {
        super();
        signLetters = new HashMap<>();
        makeSignLetters();

        //define all thresholds here
        gestureSensorMapping();

        savedDownGyro = new Gyro(0,0,0);
        savedSideGyro = new Gyro(0,0,0);
        savedUpGyro = new Gyro(0,0,0);

    }

    private void gestureSensorMapping() {
        //define threshold intervals for the 7 different positions for each of the 5 fingers
        //the thresholds are integers that correspond to flex sensor readings
        //arduino analog pin readings have 10 bit resolution, so there are 1024 values (2^10)
        //values range from 0 to 1023 with low values meaning straight and high values meaning bent
        thumb = new FlexMapping();
        thumb.addThreshold(1,0,10);
        thumb.addThreshold(2,0,0);
        thumb.addThreshold(3,0,0);
        thumb.addThreshold(4,10,20);
        thumb.addThreshold(5,0,0);
        thumb.addThreshold(6,0,0);
        thumb.addThreshold(7,0,0);

        pointer = new FlexMapping();
        pointer.addThreshold(1,10,20);
        pointer.addThreshold(2,0,0);
        pointer.addThreshold(3,0,0);
        pointer.addThreshold(4,0,0);
        pointer.addThreshold(5,0,0);
        pointer.addThreshold(6,0,10);
        pointer.addThreshold(7,0,0);

        middle = new FlexMapping();
        middle.addThreshold(1,10,20);
        middle.addThreshold(2,0,0);
        middle.addThreshold(3,0,0);
        middle.addThreshold(4,0,0);
        middle.addThreshold(5,0,0);
        middle.addThreshold(6,0,10);
        middle.addThreshold(7,0,0);

        ring = new FlexMapping();
        ring.addThreshold(1,10,20);
        ring.addThreshold(2,0,0);
        ring.addThreshold(3,0,0);
        ring.addThreshold(4,0,0);
        ring.addThreshold(5,0,0);
        ring.addThreshold(6,0,10);
        ring.addThreshold(7,0,0);

        pinkie = new FlexMapping();
        pinkie.addThreshold(1,10,20);
        pinkie.addThreshold(2,0,0);
        pinkie.addThreshold(3,0,0);
        pinkie.addThreshold(4,0,0);
        pinkie.addThreshold(5,0,0);
        pinkie.addThreshold(6,0,10);
        pinkie.addThreshold(7,0,0);

        //The letter mappings describe what position each finger should be in
        letterMappings = new ArrayList<>();
        letterMappings.add(new LetterMapping("A",1,6,6,6,6));
        letterMappings.add(new LetterMapping("B", 4, 1, 1, 1, 1));
        letterMappings.add(new LetterMapping("C", 2, 2, 2, 2, 2));
        letterMappings.add(new LetterMapping("D", 1, 1, 3, 3, 3));
        letterMappings.add(new LetterMapping("E", 4, 4, 4, 4, 4));
        letterMappings.add(new LetterMapping("F",2,3,1,1,1));
        letterMappings.add(new LetterMapping("G",1,1,4,4,4,"Side"));
        letterMappings.add(new LetterMapping("H",5,1,1,4,4,"Side"));
        letterMappings.add(new LetterMapping("I",2,6,6,6,1));
        letterMappings.add(new LetterMapping("J",2,6,6,6,1));
        letterMappings.add(new LetterMapping("K",1,1,1,6,5));
        letterMappings.add(new LetterMapping("L",1,1,6,6,6));
        letterMappings.add(new LetterMapping("M",4,5,5,5,5));
        letterMappings.add(new LetterMapping("N",5,5,5,6,6));
        letterMappings.add(new LetterMapping("O",5,5,5,5,5));
        letterMappings.add(new LetterMapping("P",1,1,1,6,5,"Down"));
        letterMappings.add(new LetterMapping("Q",1,1,5,5,5,"Down"));
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

    /**
     * populates a hash map with SignLetter objects
     */
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

        signLetters.put("None", new SignLetter(0,R.drawable.noletter));
    }

    public void connectBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //System.out.println(bluetoothAdapter.getBondedDevices());
        BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice("98:D3:71:FD:F7:6A");

        ParcelUuid list[] = hc05.getUuids();

        try {

        UUID uuid = UUID.fromString(list[0].toString());
        System.out.println("UUID FROM THE HC05: " + uuid);

        System.out.println("HC05 OBJECT: " + hc05);
        //socket code
        socket = null;

            socket = hc05.createRfcommSocketToServiceRecord(uuid);
            System.out.println("SOCKET OBJECT: " + socket);
            socket.connect();
            System.out.println("SOCKET WORKS!!!!!!!!!!!!!!!!!!!");
        } catch (IOException e) {
            System.out.println("SOCKET DIDN'T WORK!!!!!!!!!! (IO EXCEPTION/SOCKET PROBLEM)");
        } catch (NullPointerException e) {
            System.out.println("SOCKET DIDN'T WORK!!!!!!!!!! (NULL POINTER)");
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
            //inputStream = new ByteArrayInputStream("   -1 2     -332 435 -5 1 2 3    465  375   341 -2 3   4 566     1 2    ".getBytes());

            //get the string from the Bluetooth device one character at a time
            StringBuilder result = new StringBuilder();
            System.out.println("NUMBER OF BYTES AVAILABLE: " + inputStream.available());
            while(inputStream.available() > 0) {
                result.append((char) inputStream.read());
            }
            String data = result.toString();
            System.out.println("THE STRING FROM THE HC05: " + data);

            //replace multiple spaces with a single space and trim off beginning and trailing spaces so that the split function works
            String data2 = data.replaceAll(" +"," ").trim();

            //turn the string into an array of strings then convert to integers
            String[] lines = data2.split(" ");
            int[] values = new int[lines.length];
            for(int i = 0; i < values.length; i++) {
                System.out.println(lines[i]);
                values[i] = Integer.parseInt(lines[i]);
            }

            if(values.length != 17) {
                return new GloveReading(null,null,null,null,null);
            }

            //return null;
            return new GloveReading(new Flex(values[0],values[1],values[2],values[3],values[4]), new Accel(values[5],values[6],values[7]), new Accel(values[8],values[9],values[10]), new Gyro(values[11],values[12],values[13]), new Gyro(values[14],values[15],values[16]));
        }
        catch(IOException e) {
            System.out.println("READ GLOVE DIDN'T WORK!!!!!!!!!!!!!!");
        }
        catch(NullPointerException e) {
            System.out.println("NULL POINTER. YOU PROBABLY DON'T HAVE A WORKING SOCKET!");
        }
        catch(NumberFormatException e) {
            System.out.println("STRING CAN'T BECOME INTEGER");
        }
        return null;
    }

    public SignLetter determineLetter() {
        return determineLetter(readGlove());
    }

    public SignLetter determineLetter(GloveReading glove) {

        try {
            int thumbRegion = thumb.evaluate(glove.flex.thumb);
            int pointerRegion = pointer.evaluate(glove.flex.pointer);
            int middleRegion = middle.evaluate(glove.flex.middle);
            int ringRegion = ring.evaluate(glove.flex.ring);
            int pinkieRegion = pinkie.evaluate(glove.flex.pinkie);

            String orientation = determineOrientation(glove.palmGyro);

            for (LetterMapping letterMapping : letterMappings) {
                if (letterMapping.thumb == thumbRegion && letterMapping.pointer == pointerRegion && letterMapping.middle == middleRegion && letterMapping.ring == ringRegion && letterMapping.pinkie == pinkieRegion) {
                    if (letterMapping.direction.equals("None")) {
                        return signLetters.get(letterMapping.letter);
                    } else {
                        if (letterMapping.direction.equals(orientation)) {
                            return signLetters.get(letterMapping.letter);
                        }
                    }
                }
            }
        }
        catch(NullPointerException e) {
            System.out.println("GLOVE READING IS NULL!");
            return signLetters.get("None");
        }

        return signLetters.get("None");
    }

    public String determineOrientation(Gyro palmGyro) {
        double down = palmGyro.getDistance(savedDownGyro);
        double up = palmGyro.getDistance(savedUpGyro);
        double side = palmGyro.getDistance(savedSideGyro);

        if(down < up) {
            if(down < side) {
                return "Down";
            }
            else {
                return "Side";
            }
        }
        else {
            if(up < side) {
                return "Up";
            }
            else {
                return "Side";
            }
        }
    }

}
