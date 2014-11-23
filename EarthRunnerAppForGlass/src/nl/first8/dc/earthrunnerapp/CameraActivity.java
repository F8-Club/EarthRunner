package nl.first8.dc.earthrunnerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CameraActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		IntentIntegrator scanIntent = new IntentIntegrator(this);
		scanIntent.initiateScan();
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
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			Toast.makeText(
					getApplicationContext(),
					String.format("Type [%s], code [%s]", scanFormat,
							scanContent), Toast.LENGTH_LONG).show();

			Intent stepIntent = new Intent(this, StepCounterActivity.class);
			stepIntent.putExtra(AppConstants.EXTRA_CAMERA_SCAN, scanContent);
			startActivity(stepIntent);
		}
	}

}
