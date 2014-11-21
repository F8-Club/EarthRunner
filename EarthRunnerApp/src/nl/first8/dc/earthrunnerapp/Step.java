package nl.first8.dc.earthrunnerapp;

import java.io.Serializable;

public class Step implements Serializable, Comparable<Step> {
    
    private static final long serialVersionUID = 6988092714025124074L;
    private final long time;
    private final int stepCount;
    
    public Step(long time, int stepCount) {
        this.time = time;
        this.stepCount = stepCount;
    }
    
    public long getTime() {
        return time;
    }

    public int getStepCount() {
        return stepCount;
    }

    @Override
    public int compareTo(Step another) {
        if (time > another.getTime()) {
            return 1;
        } else if (time < another.getTime()) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Step [time=" + (time / 1000000) + "ms, stepCount=" + stepCount + "]";
    }

    
    

}
