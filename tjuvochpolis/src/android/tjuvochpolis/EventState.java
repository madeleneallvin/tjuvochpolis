package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventState extends PlayOrderState {
	
	boolean hasBeenTouched = false; //test
	private GameStaticObject staticObject;

	public EventState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
		Log.i("EventState", "handleState");
		
		this.staticObject.handleEvent(event);
		
		hasBeenTouched = true;
		
		
	}

	@Override
	public PlayOrderState getNextState() {
		if(hasBeenTouched) {
			hasBeenTouched = false;
			return mPlayState.mPreviousState;
		}
		return this;
	}

	/**
	 * Draw the splash screen
	 */
	public void drawSplash(Canvas c, float mZoom) {
		
		staticObject = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode().getGameStaticObject();
		staticObject.drawSplashScreen(c, mZoom);
	}
	
	@Override
	public void handleState(int frame) {
		
		
		

		
		

	}

}
