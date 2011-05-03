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
	GameObject currentObject, lastSelected = null;
	
	private Paint mPaintText;
    private Paint mPaintBgLeft;
    private Paint mPaintBgRight;
    private Rect mRectLeft;
    private Rect mRectRight;
    private Rect mRectCop1;
    private Rect mRectCop2;
    private Rect mRectCop3;
    private Bitmap mHudBottomImage;
	private Bitmap mCopImage;
	
	
	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid){	
		super(ps, gameObjects, gameStaticObjects, grid);
		
		Resources res = (ps.getContext()).getResources();
		
		mHudBottomImage = BitmapFactory.decodeResource(res, R.drawable.hud_bottom);       
        mCopImage = BitmapFactory.decodeResource(res, R.drawable.cop);
        
        mRectCop1 = new Rect();
		mRectCop2 = new Rect();
		mRectCop3 = new Rect();
	}
	@Override
	public void handleState(int frame)
	{
		
	}


//
	
	
	public void doDraw(Canvas c, float mZoom)
	{
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			//currentObject.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
		}
		
		this.drawHud(c, mZoom);
	}
	
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
		
		mRectCop1.set(0, canvasHeight - thickness, thickness, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop1, null);
		mRectCop2.set(thickness*2, canvasHeight - thickness, thickness*3, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop2, null);
		mRectCop3.set(thickness*4, canvasHeight - thickness, thickness*5, canvasHeight);
		c.drawBitmap(mCopImage, null, mRectCop3, null);
			
	}
	
	public void doTouch(View v, MotionEvent event) 
	{
		if(event.getY() > 48 && event.getY() < v.getHeight() - 48)
		{
			//om x och y är giltiga destinationer
			int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
			int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
			
			GridNode clickedNode =	mGrid.getGridNode(row, col);
			currentObject =	clickedNode.getGameObject();
	
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
		else // Fifflar på HUD:en
		{
			float x = event.getX();
			float y = event.getY();
			int thickness = mRectCop1.width();
			
			if(x > 0 && x < thickness && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
				Log.i("Cop", "1");
			else if(x > thickness*2 && x < thickness*3 && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
				Log.i("Cop", "2");
			else if(x > thickness*4 && x < thickness*5 && y > v.getHeight()-thickness && y < v.getHeight()) // Porträtt 1
				Log.i("Cop", "3");
		}
		
	}
	
	


	public PlayOrderState getNextState() {
		
	//	Log.i("COP TURN STATE", "COP1"+this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue()+"COP2"+this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue());
	//	Log.i("",""+everythingHasMoved);
		
		if(hasMoved && everythingHasMoved == false){
			hasMoved = false;
			Log.i("COP TURN STATE", " MOVE TO COP MOVE STATE");
			return mPlayState.copMoveState;
			
		}
		else if(everythingHasMoved == true){
			
			everythingHasMoved=false;
				return mPlayState.getThiefRollDiceState();
				
		} 
		
		return this;
		
	}
}
