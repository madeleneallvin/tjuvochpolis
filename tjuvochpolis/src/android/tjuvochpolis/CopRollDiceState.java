package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	public CopRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid) {
		super(ps, gameObjects , grid);
		this.mNextState = this;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleState(int frame) {
		

		//Roll dice for all cops
		this.mGameObjects.get(mObjectIndex.COP1.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		
		this.mGameObjects.get(mObjectIndex.COP2.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		
		
		//Calculate the nodeWalker
		this.mGameObjects.get(mObjectIndex.COP1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.COP2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue());
		//Change state
		this.mNextState = mPlayState.copTurnState;
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		return this.mNextState;
	}

}