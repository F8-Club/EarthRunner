package nl.first8.dc.earthrunnerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void openScanner(View view) {
		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);
	}

	public void openStepCounter(View view) {
		Intent intent = new Intent(this, StepCounterActivity.class);
		startActivity(intent);
	}

}
