package com.example.smartglove;

public class Gyro {
    public int x, y, z;

    public Gyro(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistance(Gyro other) {
        return Math.sqrt(Math.pow(this.x-other.x,2)+Math.pow(this.y-other.y,2)+Math.pow(this.z-other.z,2));
    }
}