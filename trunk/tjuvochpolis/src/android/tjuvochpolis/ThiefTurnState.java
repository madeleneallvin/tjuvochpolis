package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {
	private boolean drawSplashThief = false;
	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;

	private Rect mRectLeft;
	private Rect mRectRight;
	
	private Bitmap mHudBottomImage;
	private Bitmap mHudTopImage;

	public ThiefTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
		
		SharedPreferences mPrefs = (ps.getContext()).getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		everythingHasMoved = mPrefs.getBoolean("thiefEverythingHasMoved", false);
		hasMoved = mPrefs.getBoolean("thiefHasMoved", false);
		
		this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getCurrentDiceValue());
		
		mHudBottomImage = Bitmaps.instance(ps.getContext()).getHudBottomImageThieves();
		mHudTopImage = Bitmaps.instance(ps.getContext()).getHudTopImage();		
	}
	
	@Override
	public void handleState(int frame) {
	}

	public void doDraw(Canvas c, float mZoom) {
		if (lastSelected != null){
		if(drawSplashThief == true && lastSelected.isMoving == false){
			drawSplashScreen(c , mPlayState.getContext());
		}
		}
		
		
		this.drawHud(c, mZoom);
		
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		}
		

		
	}

	
	public void drawHud(Canvas c, float mZoom){

		int canvasWidth = (int) Math.ceil((c.getWidth()/mZoom));
		int canvasHeight = (int) Math.ceil((c.getHeight()/mZoom));
		int thickness = (int) Math.floor(48/mZoom);
		
		mRectLeft = new Rect(0, 0, canvasWidth, thickness);
		mRectRight = new Rect(0, canvasHeight-thickness, canvasWidth, canvasHeight);
		
		c.drawBitmap(mHudBottomImage, null, mRectRight, null);
	 
		c.drawBitmap(mHudTopImage, null, mRectLeft, null);
	}
	
public void drawSplashScreen(Canvas c, Context context) {

		
		Bitmaps.instance(context);
		Bitmap bankSplash = Bitmaps.getCopturnsplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect copTurnRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(bankSplash, null, copTurnRect, null);
		
		
		
	}
	
	
	public void doTouch(View v, MotionEvent event) {
		
		
		
		if(event.getY() > PlayState.HUD_TOP_HEIGHT && event.getY() < v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT){
			// Get clicked row and col
			int row = GridNode.getRow(event, mPlayState.getOffsetY());
			int col = GridNode.getCol(event, mPlayState.getOffsetX());
		
			GridNode clickedNode =	mGrid.getGridNode(row, col);
			currentObject =	clickedNode.getGameObject();
		
			//kollar om alla tjuvar har gått
		
			if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF3.getIndex()).getCurrentDiceValue() == 0){
				everythingHasMoved = true;
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