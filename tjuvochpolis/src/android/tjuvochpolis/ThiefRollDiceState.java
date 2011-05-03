package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefRollDiceState extends PlayOrderState {

	public ThiefRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid) {
		super(ps, gameObjects, gameStaticObjects, grid);
		this.mNextState = this;
		// TODO Auto-generated constructor stub
	}

	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}//

	@Override
	public void handleState(int frame) {
		// TODO Auto-generated method stub
		
		Log.i("THIEF ROLL STATE", "IT IS NOW THIEF ROLL DICE STATE");
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).setCurrentDiceValue(Dice.getDice().rollDice());
		
		//Calculate the nodeWalker
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue());
		Log.i("THIEF ROLL STATE"," did not do nodewalker");
		//Change state
		this.mNextState = mPlayState.thiefTurnState;
		
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		Log.i("THIEF TURN", "passing on to thief turn state");
		return this.mNextState;
	}

}
