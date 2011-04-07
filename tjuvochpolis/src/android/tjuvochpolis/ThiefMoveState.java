package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public class ThiefMoveState extends PlayOrderState {

	float x = 120;
	float y = 120;

	public ThiefMoveState(PlayState ps, CopObject cop, ThiefObject thief,
			Grid grid) {
		super(ps, cop, thief, grid);
	}

	public void handleState(int frame) {
		interpolatedMove(thief, frame);
	}

	public PlayOrderState getNextState() {
		if (thief.isMoving) {
			return this;
		} else {
			return mPlayState.getCopRollDiceState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

	}

}
