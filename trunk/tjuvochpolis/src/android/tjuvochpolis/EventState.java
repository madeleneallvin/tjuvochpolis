package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventState extends PlayOrderState {
	
	
	private Rect mRectLeft;
	private Rect mRectRight;
	boolean hasBeenTouched = false; //test
	private GameStaticObject staticObject;
	Context context;
	public EventState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
		Log.i("EventState", "handleState");
		
		hasBeenTouched = staticObject.handleEvent(event, context);
		
	}
	
	
public void drawHud(Canvas c, float mZoom){
		
		int canvasWidth = (int) Math.ceil((c.getWidth()/mZoom));
		int canvasHeight = (int) Math.ceil((c.getHeight()/mZoom));
		int thickness = (int) Math.floor(Grid.GRID_SIZE/mZoom);
		
		mRectLeft = new Rect(0, 0, canvasWidth, thickness);
		mRectRight = new Rect(0, canvasHeight-thickness, canvasWidth, canvasHeight);

		c.drawBitmap(HudFactory.getBottomHud(mPlayState, c), null, mRectRight, null);
		c.drawBitmap(HudFactory.getTopHud(mPlayState, c), null, mRectLeft, null);
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
		this.drawHud(c, mZoom);
		
		staticObject = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode().getGameStaticObject();

		staticObject.drawSplashScreen(c, mZoom, context);
		
	
	}
	
	@Override
	public void handleState(int frame) {

	}

}
