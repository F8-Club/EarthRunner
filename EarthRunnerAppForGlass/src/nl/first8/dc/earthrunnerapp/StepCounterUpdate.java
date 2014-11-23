package nl.first8.dc.earthrunnerapp;

import java.io.Serializable;

public class StepCounterUpdate implements Serializable {
    
    private static final long serialVersionUID = -5223640298936484962L;
    
    private float accX;
    private float accY;
    private float accZ;
    private int stepCount;
    private float stepSpeed;
    
    public StepCounterUpdate(int stepCount, float stepSpeed) {
        super();
        this.stepCount = stepCount;
        this.stepSpeed = stepSpeed;
    }

    public float getAccX() {
        return accX;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public float getAccY() {
        return accY;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public float getStepSpeed() {
        return stepSpeed;
    }

    public void setStepSpeed(float stepSpeed) {
        this.stepSpeed = stepSpeed;
    }
    

    
    

}
