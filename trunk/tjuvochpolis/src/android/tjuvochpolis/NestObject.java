package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class NestObject extends GameStaticObject {

	
	
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
	public void handleEvent(MotionEvent e) {
		if(takeMoneyButton.hasBeenClicked(e))
		{ //ger nästets pengar till tjuven
			int thiefMoney = this.getParentNode().getGameObject().getObjectMoney();
			
			this.getParentNode().getGameObject().setObjectMoney(thiefMoney+this.takeMoney());
			
		}
		if(leaveMoneyButton.hasBeenClicked(e))
		{	
			//lämnar tjuvens pengar i nästet
			this.storeMoney(this.getParentNode().getGameObject().getObjectMoney());
			this.getParentNode().getGameObject().setObjectMoney(0);
			
		}
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
		
		paint.setColor(Color.YELLOW);
		Rect button1 = new Rect(100, 350, 150, 400);
		c.drawRect(button1, paint);	
		
		paint.setColor(Color.BLUE);
		Rect button2 = new Rect(150, 350, 200, 400);
		c.drawRect(button2, paint);	
		
		takeMoneyButton = new SplashButton(100,350, 50,50);
		leaveMoneyButton = new SplashButton(170,350, 50,50);
		
	}
	
}
