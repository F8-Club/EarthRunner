package nl.first8.dc.earthrunnerapp;

import java.util.Date;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class SoftwareStepCounterListener extends StepCounterListener {

    private int totalSteps = 0;
    private int lastSteps = 0;
    private int currentSteps = 0;
    private Date lastTime = new Date();
    
    @Override
    public float getStepSpeed() {
        Date now = new Date();
        long duration = now.getTime() - lastTime.getTime();
        if (duration == 0L) {
            return 0.0f;
        }
        float minutes = duration / 60000L;
        int stepsTaken = currentSteps - lastSteps;
        float stepsPerMinute = stepsTaken / minutes;
        lastSteps = currentSteps;
        lastTime = now;
        return stepsPerMinute;
    }
    
    @Override
    public int getTotalSteps() {
        return totalSteps;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        
        //FIXME Needs to actually detect current steps, dude.
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            service.step(new Step(event.timestamp, event.values[0], event.values[1], event.values[2]));

        } 
        

    }

}
