package android.tjuvochpolis;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class BankObject extends GameStaticObject {

	public BankObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(getBankMoney());
	}

	/*
	 * Function that generates the amount of money for a bank
	 * The closer to the center the more money the bank will get
	 */
	public int getBankMoney()
	{
		float baseMoney = 10000;
		Random r = new Random();
		float rand = r.nextFloat()+1;
		
		// Map center
		int mapCenterHeight = (int)Grid.getNumCols()/2;
		int mapCenterWidth = (int)Grid.getNumRows()/2;
		
		// Bank position
		int x = this.getParentNode().getCol();
		int y = this.getParentNode().getRow();
		
		// Distance to center
		float dist = Math.abs(mapCenterHeight - x) + Math.abs(mapCenterWidth - y);
		
		// Calculate the amount of money
		int money = (int)(rand * baseMoney/dist);
		
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
		c.drawText("Bank", 75, 325, paint);
	}
}