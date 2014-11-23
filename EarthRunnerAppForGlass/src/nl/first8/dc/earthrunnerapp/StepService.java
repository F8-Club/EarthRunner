package nl.first8.dc.earthrunnerapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class StepService extends Service {

    private static final long SERVER_UPDATE_INTERVAL = 10000L;
    private final IBinder mBinder = new LocalBinder();
    private Timer timer;
    private String serverConnectionString = null; // TODO for now this is actually IP
    private SensorManager sensorManager;
    private StepCounterListener listener;

    @Override
    public IBinder onBind(Intent intent) {
        if (intent.hasExtra(AppConstants.EXTRA_CAMERA_SCAN)) {
            serverConnectionString = intent.getExtras().getString(AppConstants.EXTRA_CAMERA_SCAN);
        }
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


    @Override
    public void onCreate() {
        super.onCreate();
        registerListener();

        timer = new Timer("stepTimer", true);
        TimerTask task = new UpdateServerTask(this);
        timer.scheduleAtFixedRate(task, 3000L, SERVER_UPDATE_INTERVAL);
    }

    private void registerListener() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            listener = new StepCounterListener();
            sensorManager.registerListener(listener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Sensor senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            SensorEventListener listener = new SoftwareStepCounterListener();
            sensorManager.registerListener(listener, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(listener);
        timer.cancel();
        super.onDestroy();

    }

    public String getServerConnectionString() {
        return serverConnectionString;
    }

    public void setServerConnectionString(String serverConnectionString) {
        this.serverConnectionString = serverConnectionString;
    }

    public float getCurrentSpeed() {
        float speed = listener.getStepSpeed();
        int steps = listener.getTotalSteps();
        updateActivity(speed, steps);
        return speed;
    }

    private void updateActivity(float speed, int steps) {
        StepCounterUpdate stepUpdate = new StepCounterUpdate(steps, speed);
        Intent stepMessage = new Intent(AppConstants.BROADCAST_STEP_UPDATE);
        stepMessage.putExtra(AppConstants.BROADCAST_STEP_UPDATE_EXTRA, stepUpdate);
        sendBroadcast(stepMessage);
    }

}
