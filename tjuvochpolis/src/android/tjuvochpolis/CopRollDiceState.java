package android.tjuvochpolis;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	public CopRollDiceState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid) {
		super(ps, cop, thief, grid);
		this.mNextState = this;
		// TODO Auto-generated constructor stub
	}

	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleState(int frame) {
		// TODO Auto-generated method stub
		this.cop.setCurrentDiceValue(Dice.getDice().rollDice());
		this.cop.doNodeWalker(this.cop.getParentNode(), this.cop.getParentNode(), this.cop.getCurrentDiceValue());
		this.mNextState = mPlayState.copTurnState;
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		return this.mNextState;
	}

}
