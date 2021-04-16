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

    public GloveReading(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17) {
        this.flex = new Flex(i1,i2,i3,i4,i5);
        this.pointerAccel = new Accel(i6,i7,i8);
        this.palmAccel = new Accel(i9,i10,i11);
        this.pointerGyro = new Gyro(i12,i13,i14);
        this.palmGyro = new Gyro(i15,i16,i17);
    }
}
