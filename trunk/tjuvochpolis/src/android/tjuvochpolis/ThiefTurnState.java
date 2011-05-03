package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {

	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;
	
	private Rect mRectLeft;
	private Rect mRectRight;
	
	private Bitmap mHudBottomImage;
	private Bitmap mHudTopImage;

	public ThiefTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid) {
		super(ps, gameObjects, gameStaticObjects, grid);
		
		mHudBottomImage = Bitmaps.instance(ps.getContext()).getHudBottomImageThieves();
		mHudTopImage = Bitmaps.instance(ps.getContext()).getHudTopImage();
	}

	@Override
	public void handleState(int frame) {
	}

	public void doDraw(Canvas c, float mZoom) {
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
		}
		
		this.drawHud(c, mZoom);
	}

	public void drawHud(Canvas c, float mZoom){

		int canvasWidth = (int) Math.ceil((c.getWidth()/mZoom));
		int canvasHeight = (int) Math.ceil((c.getHeight()/mZoom));
		int thickness = (int) Math.floor(48/mZoom);
		
		mRectLeft = new Rect(0, 0, canvasWidth, thickness);
		mRectRight = new Rect(0, canvasHeight-thickness, canvasWidth, canvasHeight);
		
		c.drawBitmap(mHudBottomImage, null, mRectRight, null);
	 
		c.drawBitmap(mHudTopImage, null, mRectLeft, null);

	//	mRectCop1.set(0, canvasHeight - thickness, thickness, canvasHeight);
	//	c.drawBitmap(mCopImage, null, mRectCop1, null);
	//	mRectCop2.set(thickness*2, canvasHeight - thickness, thickness*3, canvasHeight);
	//	c.drawBitmap(mCopImage, null, mRectCop2, null);
	//	mRectCop3.set(thickness*4, canvasHeight - thickness, thickness*5, canvasHeight);
	//	c.drawBitmap(mCopImage, null, mRectCop3, null);

	}

	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;

		GridNode clickedNode =	mGrid.getGridNode(row, col);
		currentObject =	clickedNode.getGameObject();

		//kollar om alla tjuvar har gått
		if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue() == 0) {
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
		}
		else {
			lastSelected = currentObject;
		}
	}

	public PlayOrderState getNextState() {
		if(hasMoved && everythingHasMoved == false) {
			hasMoved = false;
			Log.i("THIEF TURN STATE", " MOVE TO THIEF MOVE STATE");
			
			return mPlayState.thiefMoveState;
		}
		else if(everythingHasMoved == true) {
			everythingHasMoved = false;
			
			return mPlayState.getCopRollDiceState();
		} 
		
		return this;
	} 
}