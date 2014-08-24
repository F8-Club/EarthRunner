package nl.first8.dc.earthrunnerapp;

import java.io.Serializable;


public class Step implements Serializable {
    
    private static final long serialVersionUID = 6988092714025124074L;
    private final float x;
    private final float y;
    private final float z;
    private final long time;
    
    public Step(long time, float x, float y, float z) {
        this.time = time;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public long getTime() {
        return time;
    }
    

}
