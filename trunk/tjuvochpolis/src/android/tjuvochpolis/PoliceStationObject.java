package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class PoliceStationObject extends GameStaticObject {

	public PoliceStationObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}

	@Override
	public void handleEvent() {
		

	}

	/**
	 * Function that handles the drawing of the splash screen
	 */
	@Override
	public void drawSplashScreen(Canvas c, float mZoom) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		Rect rect = new Rect(50, 300, 430, 500);
		c.drawRect(rect, paint);		
		paint.setColor(Color.WHITE);
		c.drawText("Polisstation", 75, 325, paint);
		
	}

}
