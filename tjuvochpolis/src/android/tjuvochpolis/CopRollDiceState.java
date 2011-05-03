package android.tjuvochpolis;

import java.util.ArrayList;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	public CopRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid) {
		super(ps, gameObjects, gameStaticObjects, grid);
		this.mNextState = this;
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

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
		return this.mNextState;
	}

}
