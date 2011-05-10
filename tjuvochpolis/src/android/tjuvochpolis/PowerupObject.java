package android.tjuvochpolis;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class PowerupObject extends GameStaticObject{

	public PowerupObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}


	public boolean handleEvent(MotionEvent e, Context context) {
		Log.i("PowerupObject", "You are on a power up");
		return true;
	}

	@Override
	public void drawSplashScreen(Canvas c, float mZoom, Context context) {
		// TODO Auto-generated method stub
		
	}
}
