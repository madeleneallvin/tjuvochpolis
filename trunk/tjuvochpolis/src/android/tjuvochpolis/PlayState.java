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
		
		grid.gridArray[4][4].setGameObject(cop);
		grid.gridArray[4][7].setGameObject(thief);
		
		thief.moveTo(grid.gridArray[5][7]);
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
	
	public void doTouch(View v, MotionEvent event)
	{
	}
}
