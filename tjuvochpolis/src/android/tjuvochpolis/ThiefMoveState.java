package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class ThiefMoveState extends PlayOrderState {

	float x = 120;
	float y = 120;

	public ThiefMoveState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	public void handleState(int frame) {
		interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	}

	public PlayOrderState getNextState() {	
		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving) {
			return this;
		}
		else {
			// Gets the current node
			GridNode currentNode = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode();
			
			// Depending of the type the event state is activated
			if(currentNode.getType() == GridNode.THIEF_NEST || currentNode.getType() == GridNode.BANK)
			{
				mPlayState.mPreviousState = mPlayState.getThiefTurnState();
				return mPlayState.getEventState();
			}
			
			return mPlayState.getThiefTurnState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
	}
}