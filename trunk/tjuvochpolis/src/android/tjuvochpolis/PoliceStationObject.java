package android.tjuvochpolis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class PoliceStationObject extends GameStaticObject {


	private Bitmap stationSplash;
	private SplashButton stationButton;
	
	public PoliceStationObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}

	//@Override
	public boolean handleEvent(MotionEvent e, Context context) {
		
		return true;
	}

	/**
	 * Function that handles the drawing of the splash screen
	 */
	//@Override
	public void drawSplashScreen(Canvas c, float mZoom, Context context) {
		stationSplash = Bitmaps.instance(context).getFikaSplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect stationRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(stationSplash, null, stationRect, null);
		

		

		stationButton = new SplashButton((int)(1.5*left),top+2*left, 3*left,left);
		
	}

}
