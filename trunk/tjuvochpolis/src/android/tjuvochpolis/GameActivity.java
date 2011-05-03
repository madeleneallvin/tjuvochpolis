package android.tjuvochpolis;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class GameActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
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
