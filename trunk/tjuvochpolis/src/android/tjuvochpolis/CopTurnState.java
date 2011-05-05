package android.tjuvochpolis;

import java.util.ArrayList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {

	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;
	private Rect mRectLeft;
	private Rect mRectRight;
	private Bitmap mHudBottomImage;
	private Bitmap mHudTopImage;

	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid){	
		super(ps, gameObjects, gameStaticObjects, grid);

		
		mHudBottomImage = Bitmaps.instance(ps.getContext()).getHudBottomImageCops();
		mHudTopImage = Bitmaps.instance(ps.getContext()).getHudTopImage();
	}
	@Override
	public void handleState(int frame){

	}

	public void doDraw(Canvas c, float mZoom){
	
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
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
	}
	
	public void doTouch(View v, MotionEvent event) 
	{
		if(event.getY() > PlayState.HUD_TOP_HEIGHT && event.getY() < v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT)
		{
			int row = GridNode.getRow(event, mPlayState.getOffsetY());
			int col = GridNode.getCol(event, mPlayState.getOffsetX());
			
			GridNode clickedNode =	mGrid.getGridNode(row, col);
			currentObject =	clickedNode.getGameObject();
			
			//kollar om alla poliser har gått
			if(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue() == 0){
				everythingHasMoved = true;
			}
			
			if(currentObject == null && lastSelected != null && lastSelected.getClass() == CopObject.class && lastSelected.getCurrentDiceValue() != 0){
				for(ArrayList<GridNode> paths : lastSelected.getPossiblePaths()){
					if(paths.get(paths.size() - 1).equals(mGrid.getGridNode(row, col))){ 	
						hasMoved = true;
						lastSelected.setMovePath(paths);
						lastSelected.isMoving = true;
						lastSelected.setCurrentDiceValue(0);
						setCurrentObjectSelected(lastSelected.getName());
					}

				}

			}
			else{
				lastSelected = currentObject;
			}
		}
		else{ // Fifflar på HUD:en
		
//			float x = event.getX();
//			float y = event.getY();
//			int thickness = mRectCop1.width();
//
//			if(x > 0 && x < thickness && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
//				Log.i("Cop", "1");
//			else if(x > thickness*2 && x < thickness*3 && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
//				Log.i("Cop", "2");
//			else if(x > thickness*4 && x < thickness*5 && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
//				Log.i("Cop", "3");
		}

	}
	
	public PlayOrderState getNextState() {

		if(hasMoved && everythingHasMoved == false){
			hasMoved = false;

			return mPlayState.copMoveState;

		}
		else if(everythingHasMoved == true){

			everythingHasMoved=false;
			return mPlayState.getThiefRollDiceState();

		} 

		return this;
	}
}
