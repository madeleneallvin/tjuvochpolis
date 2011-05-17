package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {
	private boolean drawSplashThief = false;
	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;

	public ThiefTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
		
		SharedPreferences mPrefs = (ps.getContext()).getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		everythingHasMoved = mPrefs.getBoolean("thiefEverythingHasMoved", false);
		hasMoved = mPrefs.getBoolean("thiefHasMoved", false);
		
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getCurrentDiceValue());		
	}
	
	@Override
	public void handleState(int frame) {
		
		
		if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getPossiblePaths().size() <= 1){
			this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).setCurrentDiceValue(0);
		}
		
		if(this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getPossiblePaths().size() <= 1){
			this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).setCurrentDiceValue(0);
		}
		
		if(this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getPossiblePaths().size() <= 1){
			this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).setCurrentDiceValue(0);
		}
		
	}

	public void doDraw(Canvas c, float mZoom) {
		if (lastSelected != null){
		if(drawSplashThief == true && lastSelected.isMoving == false){
			drawSplashScreen(c , mPlayState.getContext());
		}
		}
		
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		}
	}
	
	public void drawSplashScreen(Canvas c, Context context) {

		Bitmap bankSplash = Bitmaps.instance(context).getCopturnsplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect copTurnRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(bankSplash, null, copTurnRect, null);
		
	}
	
	public void doTouch(View v, MotionEvent event) {
		
		if(event.getY() > v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT)
		{
			if(event.getX() <= v.getWidth()*0.333)
				currentObject = mPlayState.getGameObject(mObjectIndex.THIEF1);
			else if(event.getX() > v.getWidth()*0.333 && event.getX() <= v.getWidth()*0.666)
				currentObject = mPlayState.getGameObject(mObjectIndex.THIEF2);
			else
				currentObject = mPlayState.getGameObject(mObjectIndex.THIEF3);
			
			float x =  - (currentObject.getParentNode().getX() - v.getWidth()/2);
			float y =  - (currentObject.getParentNode().getY() - (v.getHeight()/2 - 48));
			
			if(x > 0) {
				x = 0;
			}
			else if(x < (int) -(Bitmaps.instance(mPlayState.getContext()).getBackgroundImage().getWidth() - v.getWidth())) {
				x = (int) -(Bitmaps.instance(mPlayState.getContext()).getBackgroundImage().getWidth() - v.getWidth());
			}
			
			if(y > 0){
				y = 0;
			}
			else if(y < - ((Bitmaps.instance(mPlayState.getContext()).getBackgroundImage().getHeight() - v.getHeight())  + 48*2)) {
				y = -((Bitmaps.instance(mPlayState.getContext()).getBackgroundImage().getHeight() - v.getHeight())  + 48*2);
			}
			
			mPlayState.setOffsetX((int)x);
			mPlayState.setOffsetY((int)y);
			
			lastSelected = currentObject;
		}
		else if(event.getY() > PlayState.HUD_TOP_HEIGHT && event.getY() < v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT){
			// Get clicked row and col
			int row = GridNode.getRow(event, mPlayState.getOffsetY());
			int col = GridNode.getCol(event, mPlayState.getOffsetX());
		
			GridNode clickedNode =	mGrid.getGridNode(row, col);
			currentObject =	clickedNode.getGameObject();
			mPlayState.setActiveObject(currentObject);
			
			//kollar om alla tjuvar har gått
		
			if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getCurrentDiceValue() == 0){
				everythingHasMoved = true;
				Log.i("thiefTurnState ", "everythingHasMoved = true");
			}	
			
			if(currentObject != null && currentObject.getCurrentDiceValue() != 0)
			{
				currentObject.doNodeWalker(currentObject.getParentNode(), currentObject.getParentNode(), currentObject.getCurrentDiceValue());
				
				Log.i("Thief turn state"," move path size" + currentObject.getMovePath().size());
				Log.i("Thief turn state"," possible path size" + currentObject.getPossiblePaths().size());
				Log.i("Thief turn state"," dice value" + currentObject.getCurrentDiceValue());
			}
			
			if(currentObject == null && lastSelected != null && lastSelected.getClass() == ThiefObject.class && lastSelected.getCurrentDiceValue() != 0) {
					for(ArrayList<GridNode> paths : lastSelected.getPossiblePaths()) {
						if(paths.get(paths.size() - 1).equals(mGrid.getGridNode(row, col))) {
							hasMoved = true;
							lastSelected.setMovePath(paths);
							lastSelected.isMoving = true;
							lastSelected.setCurrentDiceValue(0);
							setCurrentObjectSelected(lastSelected.getName());
					}
					
				}
				if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getCurrentDiceValue() == 0){
					drawSplashThief = true;
				}
				
			}
			else{
				lastSelected = currentObject;
			}	
		}
	}
	
	
	public PlayOrderState getNextState() {		
		if(hasMoved && everythingHasMoved == false) {
			
			hasMoved = false;
			
			return mPlayState.thiefMoveState;
		}
		else if(everythingHasMoved == true) {
			drawSplashThief = false;
			everythingHasMoved = false;
			
			return mPlayState.getCopRollDiceState();
		} 
		return this;
	} 
}