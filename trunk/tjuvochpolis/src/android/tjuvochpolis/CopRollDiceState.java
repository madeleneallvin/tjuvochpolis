package android.tjuvochpolis;

import java.util.ArrayList;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	public CopRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
		this.mNextState = this;
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

	}

	@Override
	public void handleState(int frame) {
		CopObject cop1 = (CopObject) this.mGameObjects.get(mObjectIndex.COP1.getIndex());
		CopObject cop2 = (CopObject) this.mGameObjects.get(mObjectIndex.COP2.getIndex());
		
		
		//Slå tärning för poliserna.
		if(cop1.getWaitingLeft() != 0){
			cop1.setCurrentDiceValue(0);
			cop1.setWaitingLeft(cop1.getWaitingLeft()-1);
			Log.i("waiting left:", ""+cop1.getWaitingLeft());
		}else{
			cop1.setCurrentDiceValue(Dice.getDice().rollDice());
		}
		
		if(cop2.getWaitingLeft() != 0){
			cop2.setCurrentDiceValue(0);
			cop2.setWaitingLeft(cop2.getWaitingLeft()-1);
			Log.i("waiting left:", ""+cop2.getWaitingLeft());
		}else{
			cop2.setCurrentDiceValue(Dice.getDice().rollDice());
		}
		
		//Calculate the nodeWalker
		cop1.doNodeWalker(cop1.getParentNode(), cop1.getParentNode(), cop1.getCurrentDiceValue());
		cop2.doNodeWalker(cop2.getParentNode(), cop2.getParentNode(), cop2.getCurrentDiceValue());
		//Change state
		this.mNextState = mPlayState.copTurnState;
	}

	@Override
	public PlayOrderState getNextState() {
		return this.mNextState;
	}

}
