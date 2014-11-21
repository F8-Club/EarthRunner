package nl.first8.dc.earthrunnerapp;

import nl.first8.dc.earthrunnerapp.StepService.LocalBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StepCounterActivity extends ActionBarActivity {

    private StepServiceConnection connection;
    private StepUpdateReceiver updateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        Intent stepServiceIntent = new Intent(this, StepService.class);

        if (getIntent().hasExtra(AppConstants.EXTRA_CAMERA_SCAN)) {
            String content = getIntent().getExtras().getString(AppConstants.EXTRA_CAMERA_SCAN);
            stepServiceIntent.putExtra(AppConstants.EXTRA_CAMERA_SCAN, content);
        }

        connection = new StepServiceConnection();
        updateReceiver = new StepUpdateReceiver(this);
        bindService(stepServiceIntent, connection, Context.BIND_AUTO_CREATE);
        registerReceiver(updateReceiver, new IntentFilter(AppConstants.BROADCAST_STEP_UPDATE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class StepServiceConnection implements ServiceConnection {

        private boolean connected = false;
        private StepService service = null;

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service = ((LocalBinder) binder).getService();
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
            service = null;
        }

        public boolean isConnected() {
            return connected;
        }

        public StepService getService() {
            return service;
        }

    }

    class StepUpdateReceiver extends BroadcastReceiver {
        private TextView fieldAccX;
        private TextView fieldAccY;
        private TextView fieldAccZ;
        private TextView fieldSteps;
        private TextView fieldStepSpeed;

        public StepUpdateReceiver(Activity activity) {
            fieldAccX = (TextView) activity.findViewById(R.id.fieldAccX);
            fieldAccY = (TextView) activity.findViewById(R.id.fieldAccY);
            fieldAccZ = (TextView) activity.findViewById(R.id.fieldAccZ);
            fieldSteps = (TextView) activity.findViewById(R.id.fieldSteps);
            fieldStepSpeed = (TextView) activity.findViewById(R.id.fieldStepsMin);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConstants.BROADCAST_STEP_UPDATE)
                    && intent.hasExtra(AppConstants.BROADCAST_STEP_UPDATE_EXTRA)) {
                StepCounterUpdate stepUpdate =
                        (StepCounterUpdate) intent.getSerializableExtra(AppConstants.BROADCAST_STEP_UPDATE_EXTRA);
                fieldAccX.setText(String.valueOf(stepUpdate.getAccX()));
                fieldAccY.setText(String.valueOf(stepUpdate.getAccY()));
                fieldAccZ.setText(String.valueOf(stepUpdate.getAccZ()));
                fieldSteps.setText(String.valueOf(stepUpdate.getStepCount()));
                fieldStepSpeed.setText(String.valueOf(stepUpdate.getStepSpeed()));
            }
        }
    }

}
