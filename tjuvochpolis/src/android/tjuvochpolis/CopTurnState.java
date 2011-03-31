package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	
	public CopTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		super(ps, cop, thief, grid);
	}
	
	
	
	public void handleState()
	{
		
	}

	public void doTouch(View v, MotionEvent event) {
		
		//om x och y är giltiga destinationer
		hasMoved = true;
		((CopMoveState)ps.copMoveState).toCoordinates(event.getX(), event.getY());
	}


	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return ps.copMoveState;
		}
		return this;
	}
}
