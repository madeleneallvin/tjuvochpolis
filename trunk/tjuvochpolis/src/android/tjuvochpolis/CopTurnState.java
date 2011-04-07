package android.tjuvochpolis;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	
	public CopTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		super(ps, cop, thief, grid);
	}
	
	
	
	public void handleState(int frame)
	{
		//ta reda på array av gridnodes som går att gå till
		
		//rita ut möjliga gridnodes att gå till
	}

	public void doTouch(View v, MotionEvent event) {
	
		//Kasta tärning för alla pjäser
		int dice = Dice.getDice().rollDice();
		this.cop.setCurrentDiceValue(dice);
		Log.i("Dice", "" + this.cop.getCurrentDiceValue());
		
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		if(mGrid.mGridArray[row][col].getType() == 0)
		{			
			hasMoved = true;
			cop.moveToCoordinates(row, col);
			mPlayState.copMoveState.mCurrentAnimationStep = 0;
			Log.i("row, col", row + " " + col);
		}
	}

//:
	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return mPlayState.copMoveState;
		}
		return this;
	}
}
