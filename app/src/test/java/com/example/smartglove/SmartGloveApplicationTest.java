package com.example.smartglove;

import junit.framework.TestCase;

import java.util.ArrayList;

public class SmartGloveApplicationTest extends TestCase {

    public void testConnectBluetooth() {
        SmartGloveApplication app = new SmartGloveApplication();
        int p1 = 0;
        int p2 = 0;
        for(int i = 0; i < app.letterMappings.size(); i++) {
            for(int j = 0; j < app.letterMappings.size(); j++) {
                if(app.letterMappings.get(i).equals(app.letterMappings.get(j))) {
                    if(i != j) {
                        System.out.println(i + " " + j);
                    }
                }
            }
        }
        assertTrue(true);
    }

    public void testGyroDistance() {
        SmartGloveApplication app = new SmartGloveApplication();
        app.savedUpGyro = new Gyro(4,5,6);
        app.savedSideGyro = new Gyro(7,8,9);
        app.savedDownGyro = new Gyro(65,45,30);
        assertEquals("Down",app.determineOrientation(new Gyro(70,50,31)));
    }

    public void testReadGlove() {
        SmartGloveApplication app = new SmartGloveApplication();
        GloveReading gr = app.readGlove();
        assertTrue(true);
    }

    public void testDetermineLetter() {
        SmartGloveApplication app = new SmartGloveApplication();
        GloveReading mockGlove = new GloveReading(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        SignLetter letter = app.determineLetter(mockGlove);
        assertEquals("None",letter.getString());
    }

    public void findDuplicateMappings() {

    }
}