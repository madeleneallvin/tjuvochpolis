package android.tjuvochpolis;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BankObject extends GameStaticObject {

	private SplashButton rob;
	
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


	//@Override
	public boolean handleEvent(MotionEvent e, Context context) {
		
		boolean tempBool = false;
		
		//snor pengar från banken
		if(rob.hasBeenClicked(e))
		{ int thiefMoney = this.getParentNode().getGameObject().getObjectMoney();
			this.getParentNode().getGameObject().setObjectMoney(thiefMoney+this.getObjectMoney());
			this.setObjectMoney(getBankMoney());
			Log.i("NestObject", "ThiefMoney : " +thiefMoney);
			Log.i("NestObject", "bankMoney : " +this.getObjectMoney());
			tempBool = true;
			
		}
		
		return tempBool;
	}
	
	/**
	 * Function that handles the drawing of the splash screen
	 */
	@Override
	public void drawSplashScreen(Canvas c, float mZoom, Context context) {

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(24);
		paint.setTextAlign(Align.CENTER);
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		
		Bitmaps.instance(context);
		
		
		Bitmap bankSplash = Bitmaps.instance(context).getBankSplash();
		
		Rect bankRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(bankSplash, null, bankRect, null);
		c.drawText("$"+this.getObjectMoney()+ "  ", 3*left, 2*top-left, paint);
		rob = new SplashButton((int)(1.5*left),top+2*left, 3*left,left);
		
	}
}
