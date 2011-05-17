package android.tjuvochpolis;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class JailObject extends GameStaticObject {


		
	private Bitmap arrestSplash;
	private SplashButton arrestButton;
	public JailObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}

	/*
	 * Function that generates the amount of money for a bank
	 * The closer to the center the more money the bank will get
	 */
	
	
	@Override
	public boolean handleEvent(MotionEvent e, Context c) {
		return true;
	}
	
	/**
	 * Function that handles the drawing of the splash screen
	 */
	public void drawSplashScreen(Canvas c, float mZoom, Context context) {
		
		arrestSplash = Bitmaps.instance(context).getGetthiefsplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect arrestRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(arrestSplash, null, arrestRect, null);


		arrestButton = new SplashButton((int)(1.5*left),top+2*left, 3*left,left);
	}
}
