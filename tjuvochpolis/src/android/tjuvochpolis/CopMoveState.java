package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class CopMoveState extends PlayOrderState {
	
	float x = 120;
	float y = 120;
	
	
	public CopMoveState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		super(ps, cop, thief, grid);
	}
	
	public void handleState(int frame)
	{
		interpolatedMove(cop, frame);
	}
	
	public PlayOrderState getNextState()
	{
		if(cop.isMoving)
		{
			return this;
		}
		else
		{
			return mPlayState.getThiefTurnState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
	}

}
