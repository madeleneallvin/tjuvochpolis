package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class CopMoveState extends PlayOrderState {
	
	float colCoordinate = 120;
	float rowCoordinate = 120;
	
	public CopMoveState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		super(ps, cop, thief, grid);
	}
	
	public void move() {
		int column = (int)Math.floor(colCoordinate/48); //30 är "lagom" storlek för punkterna som ritas ut
		int row = (int)Math.floor(rowCoordinate/48);
		this.cop.moveTo(this.grid.gridArray[row][column]);
	}
	
	public void toCoordinates(float rowCoordinate, float colCoordinate)
	{
		this.colCoordinate = colCoordinate;
		this.rowCoordinate = rowCoordinate;
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
