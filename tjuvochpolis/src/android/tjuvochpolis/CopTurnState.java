package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected;
	
	private Paint mPaintText;
    private Paint mPaintBgLeft;
    private Paint mPaintBgRight;
    private Rect mRectLeft;
    private Rect mRectRight;
    private Rect mRectCop;
    private Bitmap mHudBottomImage;
	private Bitmap mCopImage;
	
	//public CopTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid){	
		super(ps, gameObjects , grid);
		
		Resources res = (ps.getContext()).getResources();
		
		mHudBottomImage = BitmapFactory.decodeResource(res, R.drawable.hud_bottom);       
        mCopImage = BitmapFactory.decodeResource(res, R.drawable.cop);		
	}
	
	public void handleState(int frame)
	{
		//ta reda på array av gridnodes som går att gå till
		
		//rita ut möjliga gridnodes att gå till
	}

	public void doDraw(Canvas c, float mZoom)
	{// If cop is clicked -> draw highlights
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			//mGameObjects.get(mObjectIndex.valueOf(tempIndex).getIndex()).drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
			currentObject.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
		}
		
		this.drawHud(c, mZoom);
	}
	
	//public void moveTo(float x, float y);
	public void drawHud(Canvas c, float mZoom)
	{
		
		int canvasWidth = (int) Math.ceil((c.getWidth()/mZoom));
		int canvasHeight = (int) Math.ceil((c.getHeight()/mZoom));
		int thickness = (int) Math.floor(48/mZoom);
		
		mRectLeft = new Rect(0, 0, canvasWidth, thickness);
		
		mRectRight = new Rect(0, canvasHeight-thickness, canvasWidth, canvasHeight);

		c.drawBitmap(mHudBottomImage, null, mRectRight, null);
		
		int width = mHudBottomImage.getWidth();
		int height = mHudBottomImage.getHeight();
        Matrix matrixLeft = new Matrix();
        matrixLeft.postRotate(180);
        
        Bitmap resizedBitmapLeft = Bitmap.createBitmap(mHudBottomImage, 0, 0, width, height, matrixLeft, true); 
		
        
		c.drawBitmap(resizedBitmapLeft, null, mRectLeft, null);
		
		mRectCop = new Rect(0, canvasHeight - thickness, thickness, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop, null);
		mRectCop = new Rect(thickness*2, canvasHeight - thickness, thickness*3, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop, null);
		mRectCop = new Rect(thickness*4, canvasHeight - thickness, thickness*5, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop, null);
			
	}
	
	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		GridNode clickedNode =	mGrid.getGridNode(row, col);
		currentObject =	clickedNode.getGameObject();
		// If cop is clicked -> draw highlights
		
	//	if(currentObject != null && currentObject.getClass() == CopObject.class)
		//{
			
			
		//}
		
		//kollar om alla poliser har gått
		if(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue() == 0){
			everythingHasMoved = true;
		}
	
		
		
		if(currentObject == null && lastSelected != null && lastSelected.getClass() == CopObject.class && lastSelected.getCurrentDiceValue() != 0)
		{
			for(ArrayList<GridNode> paths : lastSelected.getPossiblePaths())
			{
				if(paths.get(paths.size() - 1).equals(mGrid.getGridNode(row, col)))
				{ 	
					//Log.i("SELECTED","" + getCurrentObjectSelected());
					hasMoved = true;
					lastSelected.setMovePath(paths);
					lastSelected.isMoving = true;
					lastSelected.setCurrentDiceValue(0);
					setCurrentObjectSelected(lastSelected.getName());
				}
				
			
				//tempIndex = " ";
			}
		
		}
		else{
			lastSelected = currentObject;
		}
		
	}

//:
	public PlayOrderState getNextState() {
		
		if(hasMoved && everythingHasMoved == false){
			hasMoved = false;
			return mPlayState.copMoveState;
		}
		else if(everythingHasMoved == true){
			return mPlayState.getThiefRollDiceState();
		} 
		return this;
		
	}
}
