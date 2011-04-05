package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	public CopRollDiceState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid) {
		super(ps, cop, thief, grid);
		this.nextState = this;
		// TODO Auto-generated constructor stub
	}

	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		this.cop.currentDiceValue = Dice.getDice().rollDice();
		this.cop.nodeWalker(this.cop.parentNode, this.cop.parentNode, this.cop.currentDiceValue);
		
		this.nextState = ps.copTurnState;
	}

	@Override
	public void handleState(int frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		return this.nextState;
	}

}
