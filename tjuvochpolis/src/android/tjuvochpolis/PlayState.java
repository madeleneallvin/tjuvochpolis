package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class PlayState implements GameState
{
    private Grid grid;
    private CopObject cop;
	private ThiefObject thief;
	
	public PlayState()
	{
		grid = new Grid();
		
		cop = new CopObject(grid.gridArray[4][4]);
		thief = new ThiefObject(grid.gridArray[4][7]);
		
		
		
		
		//thief.moveTo(grid.gridArray[5][7]);
	}
	
	public void handleState(Canvas canvas)
	{
		draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		
	}
	
	private void draw(Canvas c)
	{
		c.drawColor(Color.DKGRAY);
		cop.doDraw(c);
		thief.doDraw(c);
	}
	
	public void moveTo(float x, float y)
	{
		int column = (int)Math.floor(x/30);
		int row = (int)Math.floor(y/30);
		cop.moveTo(grid.gridArray[column][row]);
	}
	//movement of a game object
	public void doTouch(View v, MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
				
		
		moveTo(x, y);
	}
}
