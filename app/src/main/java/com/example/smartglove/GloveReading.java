package com.example.smartglove;

public class GloveReading {

    public Flex flex;
    public Accel thumbAccel, pointerAccel, middleAccel;
    public Gyro thumbGyro, pointerGyro, middleGyro;

    public GloveReading(Flex flex, Accel thumbAccel, Accel pointerAccel, Accel middleAccel, Gyro thumbGyro, Gyro pointerGyro, Gyro middleGyro) {
        this.flex = flex;
        this.thumbAccel = thumbAccel;
        this.pointerAccel = pointerAccel;
        this.middleAccel = middleAccel;
        this.thumbGyro = thumbGyro;
        this.pointerGyro = pointerGyro;
        this.middleGyro = middleGyro;
    }
}
