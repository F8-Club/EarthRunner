package nl.first8.dc.earthrunnerapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class StepListener implements SensorEventListener {

    private static StepListener instance;
    private StepService service;

    public StepListener(StepService service) {
        this.service = service;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            service.step(new Step(event.timestamp, event.values[0], event.values[1], event.values[2]));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.i("StepListener", "AcceleroMeter sensitivity changed to: " + accuracy);
        }

    }

    public static SensorEventListener getInstance(StepService service) {
        if (instance == null) {
            instance = new StepListener(service);
        }
        return instance;
    }

}
