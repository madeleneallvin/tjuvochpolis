package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected;
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
	{// If cop is clicked -> draw highlights
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			//mGameObjects.get(mObjectIndex.valueOf(tempIndex).getIndex()).drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
			currentObject.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
		}
	}
	
	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y �r giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		GridNode clickedNode =	mGrid.getGridNode(row, col);
		currentObject =	clickedNode.getGameObject();
		// If cop is clicked -> draw highlights
		
	//	if(currentObject != null && currentObject.getClass() == CopObject.class)
		//{
			
			
		//}
		
		//kollar om alla poliser har g�tt
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
