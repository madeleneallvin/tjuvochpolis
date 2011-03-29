package android.tjuvochpolis;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MenuState implements GameState
{
	int color = -16711681;
	int nextState = 0;
	
	public MenuState()
	{
		
	}
	
	public void handleState(Canvas canvas)
	{
		color--;
		draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		if(nextState == 1)
			gt.setCurrentState(gt.getPlayState());
	}
	
	private void draw(Canvas c)
	{
		c.drawColor(color);
	}
	
	public void doTouch(View v, MotionEvent event)
	{
		nextState = 1;
	}
	
}
