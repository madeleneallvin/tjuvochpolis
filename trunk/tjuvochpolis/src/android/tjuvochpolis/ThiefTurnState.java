package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {

	public ThiefTurnState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid) {
		super(ps, gameObjects , grid);
	}
	
	boolean hasMoved = false;
	String tempIndex = " ";	
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	public void doDraw(Canvas c)
	{
		if(tempIndex != " "){
		
		mGameObjects.get(mObjectIndex.valueOf(tempIndex).getIndex()).drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		
		}
		}
	
	@Override
	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		
		GridNode clickedNode =	mGrid.getGridNode(row, col);
		GameObject currentObject =	clickedNode.getGameObject();
		// If cop is clicked -> draw highlights
		
		if(currentObject != null && currentObject.getClass() == ThiefObject.class){
	
	
		tempIndex = currentObject.getName();
		
		Log.i("CURRENTOBJECT.NAME","" +currentObject.getName());
		Log.i("TEMPINDEX", "" + tempIndex);
		
		}
		else{
			tempIndex = " ";
			
		}
	
		
				
		
		
		//if(tempIndex != " "){
		
		//Här ska det kollas att man valt en "mPossiblePaths" (istället för bara ...getType == 0
	//	if(mGrid.mGridArray[row][col].getType() == 0)
	//	{			
		//	hasMoved = true;
		//	currentObject.moveToCoordinates(row, col);
			//mPlayState.copMoveState.mCurrentAnimationStep = 0;
		
		//}
	//	}
	
			
		}
	


	@Override
	public void handleState(int frame) {
		// TODO Auto-generated method stub
		
	}


//:
	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return mPlayState.thiefMoveState;
		}
		return this;
	}
}