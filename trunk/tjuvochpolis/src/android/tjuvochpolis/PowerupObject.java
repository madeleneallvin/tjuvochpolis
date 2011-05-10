package android.tjuvochpolis;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class PowerupObject extends GameStaticObject{

	public PowerupObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}


	public void handleEvent(MotionEvent e) {
		Log.i("PowerupObject", "You are on a power up");
		
	}

	@Override
	public void drawSplashScreen(Canvas c, float mZoom) {
		// TODO Auto-generated method stub
		
	}
}
