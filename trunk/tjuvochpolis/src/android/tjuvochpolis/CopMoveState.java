package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class CopMoveState extends PlayOrderState {
	
	float x = 120;
	float y = 120;
	
	public CopMoveState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		super(ps, cop, thief, grid);
	}
	
	public void move() {
		int column = (int)Math.floor(x/48); //30 är "lagom" storlek för punkterna som ritas ut
		int row = (int)Math.floor(y/48);
		this.cop.moveTo(this.grid.gridArray[column][row]);
	}
	
	public void toCoordinates(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void handleState()
	{
		move();
	}
	
	public PlayOrderState getNextState()
	{
		return ps.getCopTurnState();
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
	}

}
