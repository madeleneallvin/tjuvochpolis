package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class ThiefRollDiceState extends PlayOrderState {

	public ThiefRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid) {
		super(ps, gameObjects , grid);
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
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		
		//Calculate the nodeWalker
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue());
		
		//Change state
		this.mNextState = mPlayState.thiefTurnState;
		
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		return mPlayState.getThiefTurnState();
	}

}
