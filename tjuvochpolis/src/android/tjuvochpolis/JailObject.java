package android.tjuvochpolis;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class JailObject extends GameStaticObject {

	private SplashButton rob;
	
	public JailObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}

	/*
	 * Function that generates the amount of money for a bank
	 * The closer to the center the more money the bank will get
	 */
	
	
	@Override
	public boolean handleEvent(MotionEvent e, Context c) {
		Log.i("handleEvent", "jailobject");
		return true;
	}
	
	/**
	 * Function that handles the drawing of the splash screen
	 */
	public void drawSplashScreen(Canvas c, float mZoom, Context con) {
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		Rect rect = new Rect(50, 300, 430, 500);
		c.drawRect(rect, paint);		
		paint.setColor(Color.RED);
		c.drawText("Jail", 75, 325, paint);
		paint.setColor(Color.BLUE);
		Rect button = new Rect(100, 350, 150, 400);
		c.drawRect(button, paint);		
		
		rob = new SplashButton(100,350, 50,50);
	}
}
