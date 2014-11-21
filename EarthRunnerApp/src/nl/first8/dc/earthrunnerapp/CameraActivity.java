package nl.first8.dc.earthrunnerapp;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CameraActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        IntentIntegrator scanIntent = new IntentIntegrator(this);
        scanIntent.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
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
    
    

    @Override
    public void onBackPressed() {
        Intent stepIntent = new Intent(this, MainActivity.class);
        startActivity(stepIntent);
        super.onBackPressed();
    }

    public void onScan(View view) {
        IntentIntegrator scanIntent = new IntentIntegrator(this);
        scanIntent.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Toast.makeText(getApplicationContext(), String.format("Type [%s], code [%s]", scanFormat, scanContent),
                    Toast.LENGTH_LONG).show();
            
            Intent stepIntent = new Intent(this, StepCounterActivity.class);
            stepIntent.putExtra(AppConstants.EXTRA_CAMERA_SCAN, scanContent);
            startActivity(stepIntent);
        }
    }
}
