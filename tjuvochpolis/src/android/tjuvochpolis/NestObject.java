package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class NestObject extends GameStaticObject {

	public NestObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(0);
	}

	/*
	 * Function to store away money in the nest
	 */
	public void storeMoney(int money){
		this.setObjectMoney(this.getObjectMoney() + money);
	}
	
	/*
	 * Function to take all the money from the nest
	 */
	public int takeMoney(){
		int money = this.getObjectMoney();
		this.setObjectMoney(0);
		return money;
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
		c.drawText("Tjuvnäste", 75, 325, paint);
		
	}
	
}
