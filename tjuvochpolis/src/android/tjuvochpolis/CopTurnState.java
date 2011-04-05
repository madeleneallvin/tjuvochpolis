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
		
	}

	public void doTouch(View v, MotionEvent event) {
		
		//Kasta tärning för alla pjäser
		int dice = Dice.getDice().rollDice();
		this.cop.currentDiceValue = dice;
		Log.i("Dice", "" + this.cop.currentDiceValue);
		
		//om x och y är giltiga destinationer
		int row = (int) Math.floor(event.getY()/Grid.GRID_SIZE);
		int col = (int) Math.floor(event.getX()/Grid.GRID_SIZE);
		
		if(grid.gridArray[row][col].getType() == 0)
		{			
			hasMoved = true;
			cop.moveToCoordinates(row, col);
			ps.copMoveState.mCurrentAnimationStep = 0;
			Log.i("row, col", row + " " + col);
		}
	}

//:
	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return ps.copMoveState;
		}
		return this;
	}
}
