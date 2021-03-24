package com.example.smartglove;

public class GloveReading {

    public Flex flex;
    public Accel palmAccel, pointerAccel;
    public Gyro palmGyro, pointerGyro;

    public GloveReading(Flex flex, Accel palmAccel, Accel pointerAccel, Gyro palmGyro, Gyro pointerGyro) {
        this.flex = flex;
        this.pointerAccel = pointerAccel;
        this.palmAccel = palmAccel;
        this.pointerGyro = pointerGyro;
        this.palmGyro = palmGyro;
    }
}
