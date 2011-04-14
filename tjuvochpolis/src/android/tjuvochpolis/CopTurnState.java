package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	GameObject currentObject, lastSelected;
	//public CopTurnState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid){	
		super(ps, gameObjects , grid);
		
		
	}
	
	
	
	public void handleState(int frame)
	{
		//ta reda på array av gridnodes som går att gå till
		
		//rita ut möjliga gridnodes att gå till
	}

	public void doDraw(Canvas c)
	{
		if(currentObject != null)
		{
			//mGameObjects.get(mObjectIndex.valueOf(tempIndex).getIndex()).drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
			currentObject.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		}
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
		
		
		if(currentObject == null && lastSelected != null && lastSelected.getClass() == CopObject.class)
		{
			for(ArrayList<GridNode> paths : lastSelected.getPossiblePaths())
			{
				if(paths.get(paths.size() - 1).equals(mGrid.getGridNode(row, col)))
				{ 	
					Log.i("SELECTED","" + getCurrentObjectSelected());
					hasMoved = true;
					lastSelected.setMovePath(paths);
					lastSelected.isMoving = true;
					
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
		if(hasMoved){
			hasMoved = false;
			return mPlayState.copMoveState;
		}
		return this;
	}
}
