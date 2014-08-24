package nl.first8.dc.earthrunnerapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class StepService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Class used for the client Binder. Because we know this service always runs in the same process as its clients, we
     * don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        StepService getService() {
            // Return this instance of LocalService so clients can call public methods
            return StepService.this;
        }
    }

    public void step(Step step) {
        Log.d("StepService", String.format("AccX - %s, AccY - %s, AccZ - %s", step.getX(), step.getY(), step.getZ()));
        Intent stepMessage = new Intent("stepUpdate");
        stepMessage.putExtra("step", step);
        sendBroadcast(stepMessage);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = (Sensor) senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(StepListener.getInstance(this), senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        senSensorManager.unregisterListener(StepListener.getInstance(this), senAccelerometer);
        super.onDestroy();
        
    }

}
