package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class NestObject extends GameStaticObject {

	private Rect nestRect;
	private Bitmap nestSplash;
	private SplashButton takeMoneyButton,leaveMoneyButton;
	
	
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
	public boolean handleEvent(MotionEvent e, Context context) {
		boolean tempBool = false;
		
		if(takeMoneyButton.hasBeenClicked(e))
		{ //ger nästets pengar till tjuven
			int thiefMoney = this.getParentNode().getGameObject().getObjectMoney();
			//sätter pengar på tjuvens ficka
			this.getParentNode().getGameObject().setObjectMoney(thiefMoney+this.takeMoney());
			Log.i("NestObject", "ThiefMoney : " +thiefMoney);
			Log.i("NestObject", "nestMoney : " +this.getObjectMoney());
			tempBool = true;
			
			
		}
		if(leaveMoneyButton.hasBeenClicked(e))
		{	tempBool = true;
			//lämnar tjuvens pengar i nästet
			this.storeMoney(this.getParentNode().getGameObject().getObjectMoney());
			this.getParentNode().getGameObject().setObjectMoney(0);
			
			int thiefMoney = this.getParentNode().getGameObject().getObjectMoney();
			Log.i("NestObject", "ThiefMoney : " +thiefMoney);
			Log.i("NestObject", "nestMoney : " +this.getObjectMoney());
		}
		
		return tempBool;
	}

	/**
	 * Function that handles the drawing of the splash screen
	 */
	//@Override
	public void drawSplashScreen(Canvas c, float mZoom, Context context ) {
				
		
		Bitmaps.instance(context);
		nestSplash = Bitmaps.getNestSplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect nestRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(nestSplash, null, nestRect, null);
		
//		
		
		//paint.setColor(Color.BLACK);
/*				
		paint.setColor(Color.WHITE);
		c.drawText("Tjuvnäste", 75, 325, paint);
		
		//temporära knappar
		paint.setColor(Color.YELLOW);
		Rect button1 = new Rect(100, 350, 150, 400);
		c.drawRect(button1, paint);	
		
		paint.setColor(Color.BLUE);
		Rect button2 = new Rect(150, 350, 200, 400);
		c.drawRect(button2, paint);	
	*/
		
		//takeMoneyButton = new SplashButton(120,335, 260,68);
		leaveMoneyButton = new SplashButton((int)(1.5*left),top+2*left, 3*left,left);
		
	}
	
}
