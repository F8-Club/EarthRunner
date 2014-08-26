package nl.first8.dc.earthrunnerapp;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class StepService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Timer timer;
    private Random random = new Random();
    private String uuid = "empty";

    @Override
    public IBinder onBind(Intent intent) {
        uuid = intent.getExtras().getString("uuid");
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
        // Log.d("StepService", String.format("AccX - %s, AccY - %s, AccZ - %s", step.getX(), step.getY(),
        // step.getZ()));
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
                SensorManager.SENSOR_DELAY_FASTEST);
        timer = new Timer("stepTimer", true);
        TimerTask task = new UpdateServerTask(this);
        timer.scheduleAtFixedRate(task, 3000L, 1000L);
    }

    @Override
    public void onDestroy() {
        senSensorManager.unregisterListener(StepListener.getInstance(this), senAccelerometer);
        timer.cancel();
        super.onDestroy();

    }

    public float getCurrentSpeed() {
        return random.nextFloat() * 5;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
