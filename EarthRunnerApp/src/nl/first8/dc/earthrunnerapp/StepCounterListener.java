package nl.first8.dc.earthrunnerapp;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class StepCounterListener implements SensorEventListener {
    
    private final NavigableSet<Step> steps = new ConcurrentSkipListSet<Step>(); 
    private static final long ONE_BILLION = 1000000000L;
    private static final long RETENTION = 60 * ONE_BILLION; 


    public float getStepSpeed() {
        Step first = steps.first();
        Step last = steps.last();
        int stepsTaken = last.getStepCount() - first.getStepCount();
        long duration = last.getTime() - first.getTime();
        if (duration == 0L) {
            return 0.0f;
        }
        float durationSeconds = duration / ONE_BILLION;
        float minutes = durationSeconds / 60;
        float stepsPerMinute = stepsTaken / minutes;
        return stepsPerMinute;
    }
    
    public int getTotalSteps() {
        if (steps.size() == 0) {
            return 0;
        }
         return steps.last().getStepCount();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("StepListener", sensor.getName() + " accuracy changed to: " + accuracy);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_STEP_COUNTER == event.sensor.getType()) {
            Step step = new Step(event.timestamp, (int) event.values[0]);
            steps.add(step);
            removeOldSteps(event.timestamp);
        }
       
    }

    private void removeOldSteps(long timestamp) {
        Step cutOff = new Step(timestamp - RETENTION, 0);
        SortedSet<Step> headSet = steps.headSet(cutOff);
        steps.removeAll(headSet);
        
    }
    

}
