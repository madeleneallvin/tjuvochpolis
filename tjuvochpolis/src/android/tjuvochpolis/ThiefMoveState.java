package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class ThiefMoveState extends PlayOrderState {

	float x = 120;
	float y = 120;

	public ThiefMoveState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid) {
		super(ps, gameObjects , grid);
	}


	public void handleState(int frame) {
		interpolatedMove(mGameObjects.get(mObjectIndex.THIEF1.getIndex()), frame);
	}

	public PlayOrderState getNextState() {
		if (mGameObjects.get(mObjectIndex.THIEF1.getIndex()).isMoving) {
			return this;
		} else {
			return mPlayState.getCopRollDiceState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

	}

}
