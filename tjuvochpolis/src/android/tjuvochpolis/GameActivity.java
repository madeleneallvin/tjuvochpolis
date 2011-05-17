package android.tjuvochpolis;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main);		 
	}
	 
	@Override
	public void onStop() {

		super.onStop();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override 
	public void onSaveInstanceState(Bundle savedInstanceState) { 
		super.onSaveInstanceState(savedInstanceState);
		
	}
	
	@Override 
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
	}
	
}
