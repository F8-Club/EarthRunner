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

        Intent intent = new Intent(this, StepService.class);
        connection = new StepServiceConnection();
        updateReceiver = new StepUpdateReceiver(this);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        registerReceiver(updateReceiver, new IntentFilter("stepUpdate"));
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

        public StepUpdateReceiver(Activity activity) {
            fieldAccX = (TextView) activity.findViewById(R.id.fieldAccX);
            fieldAccY = (TextView) activity.findViewById(R.id.fieldAccY);
            fieldAccZ = (TextView) activity.findViewById(R.id.fieldAccZ);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("stepUpdate") && intent.hasExtra("step")) {
                Step step = (Step) intent.getSerializableExtra("step");
                fieldAccX.setText(Float.valueOf(step.getX()).toString());
                fieldAccY.setText(Float.valueOf(step.getY()).toString());
                fieldAccZ.setText(Float.valueOf(step.getZ()).toString());

            }

        }
    }

}
