package android.tjuvochpolis;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class SplashButton {

	
	Rect button = new Rect(100, 350, 150, 400);
	public SplashButton(int startX, int startY, int width, int height){
		button = new Rect(startX, startY, startX + width, startY + height);
	}
	
	
	public boolean hasBeenClicked(MotionEvent event)
	{
		if(event.getX() > button.left && event.getX() < button.right
				&& event.getY() > button.top && event.getY() < button.bottom) {
			return true;	
		}
		else{
			return false;
		}
	}
	
	
}
