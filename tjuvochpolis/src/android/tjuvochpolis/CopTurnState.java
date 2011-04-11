package android.tjuvochpolis;

import android.graphics.Canvas;
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
		//ta reda p� array av gridnodes som g�r att g� till
		
		//rita ut m�jliga gridnodes att g� till
	}

	public void doDraw(Canvas c)
	{
		mPlayState.cop.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
	}
	
	public void doTouch(View v, MotionEvent event) {
	
		//Kasta t�rning f�r alla pj�ser
	//	int dice = Dice.getDice().rollDice();
	//	this.cop.setCurrentDiceValue(dice);
	//	Log.i("Dice", "" + this.cop.getCurrentDiceValue());
		
	//	this.cop.nodeWalker(this.cop.getParentNode(), this.cop.getParentNode(), this.cop.getCurrentDiceValue());
		
		Log.i("CopTurnState", "Waiting for a click");
		//om x och y �r giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		//H�r ska det kollas att man valt en "mPossiblePaths" (ist�llet f�r bara ...getType == 0
		if(mGrid.mGridArray[row][col].getType() == 0)
		{			
			hasMoved = true;
			cop.moveToCoordinates(row, col);
			mPlayState.copMoveState.mCurrentAnimationStep = 0;
			Log.i("CopTurnState", "has moved");
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
