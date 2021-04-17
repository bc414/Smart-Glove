package com.example.smartglove;

import java.util.ArrayList;

public class FlexMapping {

    class Threshold {
        public int id;
        public int low;
        public int high;

        public Threshold(int id, int low, int high) {
            this.id = id;
            this.low = low;
            this.high = high;
        }
    }

    ArrayList<Threshold> thresholds;

    public FlexMapping() {
        thresholds = new ArrayList<>();
    }

    public void addThreshold(int id, int low, int high) {
        thresholds.add(new Threshold(id, low, high));
    }

    /**
     * Returns 0 if the value doesn't match any thresholds
     * @param value
     * @return
     */
    public int evaluate(int value) {
        for(Threshold threshold : thresholds) {
            if(value >= threshold.low && value <= threshold.high) {
                return threshold.id;
            }
        }
        return 0;
    }

}
