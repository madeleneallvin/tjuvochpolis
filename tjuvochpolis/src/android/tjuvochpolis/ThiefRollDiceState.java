package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefRollDiceState extends PlayOrderState {

	public ThiefRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
		this.mNextState = this;
	}

	public void moveTo(float x, float y) {
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
	}

	@Override
	public void handleState(int frame) {
		ThiefObject thief1 = (ThiefObject) this.mGameObjects.get(mObjectIndex.THIEF1.getIndex());
		ThiefObject thief2 = (ThiefObject) this.mGameObjects.get(mObjectIndex.THIEF2.getIndex());
		ThiefObject thief3 = (ThiefObject) this.mGameObjects.get(mObjectIndex.THIEF3.getIndex());
		
		
		//Slå tärning för tjyvarna.
		if(thief1.getWaitingLeft() != 0){
			thief1.setCurrentDiceValue(0);
			thief1.setWaitingLeft(thief1.getWaitingLeft()-1);
			thief1.setDrawWaitingLeft(true);
			Log.i("waiting left:", ""+thief1.getWaitingLeft());
		}else{
			thief1.setRolledDiceValue(Dice.getDice().rollDice());
			thief1.setDrawWaitingLeft(false);
		}
		
		if(thief2.getWaitingLeft() != 0){
			thief2.setCurrentDiceValue(0);
			thief2.setWaitingLeft(thief2.getWaitingLeft()-1);
			thief2.setDrawWaitingLeft(true);
			Log.i("waiting left:", ""+thief2.getWaitingLeft());
		}else{
			thief2.setRolledDiceValue(Dice.getDice().rollDice());
			thief2.setDrawWaitingLeft(false);
		}
		
		if(thief3.getWaitingLeft() != 0){
			thief3.setCurrentDiceValue(0);
			thief3.setWaitingLeft(thief3.getWaitingLeft()-1);
			thief3.setDrawWaitingLeft(true);
			Log.i("waiting left:", ""+thief3.getWaitingLeft());
		}else{
			thief3.setRolledDiceValue(Dice.getDice().rollDice());
			thief3.setDrawWaitingLeft(false);
		}
		
		//Calculate the nodeWalker
		thief1.doNodeWalker(thief1.getParentNode(), thief1.getParentNode(), thief1.getCurrentDiceValue());
		thief2.doNodeWalker(thief2.getParentNode(), thief2.getParentNode(), thief2.getCurrentDiceValue());
		thief3.doNodeWalker(thief3.getParentNode(), thief3.getParentNode(), thief3.getCurrentDiceValue());

		//Change state
		this.mNextState = mPlayState.thiefTurnState;
	}

	@Override
	public PlayOrderState getNextState() {
		return this.mNextState;
	}
}
