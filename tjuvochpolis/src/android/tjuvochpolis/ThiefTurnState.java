package android.tjuvochpolis;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {

	public ThiefTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid) {
		super(ps, cop, thief, grid);
	}
	
	boolean hasMoved = false;

	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void handleState(int frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doTouch(View v, MotionEvent event) {
		
		//Kasta tärning för alla pjäser
		int dice = Dice.getDice().rollDice();
		this.thief.setCurrentDiceValue(dice);
		Log.i("Dice", "" + this.thief.getCurrentDiceValue());
		
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		if(mGrid.mGridArray[row][col].getType() == 0)
		{			
			hasMoved = true;
			thief.moveToCoordinates(row, col);
			mPlayState.thiefMoveState.mCurrentAnimationStep = 0;
			Log.i("row, col", row + " " + col);
		}
	}

//:
	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return mPlayState.thiefMoveState;
		}
		return this;
	}
}