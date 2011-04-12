package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	String tempIndex = " ";	
	//public CopTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid){	
		super(ps, gameObjects , grid);
		
		
	}
	
	
	
	public void handleState(int frame)
	{
		//ta reda p� array av gridnodes som g�r att g� till
		
		//rita ut m�jliga gridnodes att g� till
	}

	public void doDraw(Canvas c)
	{
		if(tempIndex != " "){
		
		mGameObjects.get(mObjectIndex.valueOf(tempIndex).getIndex()).drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		
		}
		}
	
	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y �r giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		
		GridNode clickedNode =	mGrid.getGridNode(row, col);
		GameObject currentObject =	clickedNode.getGameObject();
		// If cop is clicked -> draw highlights
		
		if(currentObject != null && currentObject.getClass() == CopObject.class){
	
	
		tempIndex = currentObject.getName();
		
		//Log.i("CURRENTOBJECT.NAME","" +currentObject.getName());
		//Log.i("TEMPINDEX", "" + tempIndex);
		
		}
		else{
			tempIndex = " ";
			
		}
	
		
				
		
		
		//if(tempIndex != " "){
		
		//H�r ska det kollas att man valt en "mPossiblePaths" (ist�llet f�r bara ...getType == 0
	//	if(mGrid.mGridArray[row][col].getType() == 0)
	//	{			
		//	hasMoved = true;
		//	currentObject.moveToCoordinates(row, col);
			//mPlayState.copMoveState.mCurrentAnimationStep = 0;
		
		//}
	//	}
	
			
		}
	


	public PlayOrderState getNextState() {
		if(hasMoved){
			hasMoved = false;
			return mPlayState.copMoveState;
		}
		return this;
	}
}
