package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
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
		//Roll the dice
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
		return this.mNextState;
	}
}
