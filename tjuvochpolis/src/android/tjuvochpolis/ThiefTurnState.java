package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefTurnState extends PlayOrderState {

	public ThiefTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid) {
		super(ps, gameObjects, gameStaticObjects, grid);
	}
	boolean hasMoved = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;
	
	
	@Override
	public void handleState(int frame) {

	}
	

	public void doDraw(Canvas c, float mZoom)
	{
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			//currentObject.drawHighlightSquare(c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY()-48);
		}
	}//
	
	public void doTouch(View v, MotionEvent event) 
	{
		//om x och y är giltiga destinationer
		int row = ((int) event.getY() - mPlayState.getOffsetY())/Grid.GRID_SIZE;
		int col = ((int) event.getX() - mPlayState.getOffsetX())/Grid.GRID_SIZE;
		
		
		GridNode clickedNode =	mGrid.getGridNode(row, col);
		currentObject =	clickedNode.getGameObject();
		
		
		//kollar om alla tjuvar har gått
		if(this.mGameObjects.get(mObjectIndex.THIEF1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.THIEF2.getIndex()).getCurrentDiceValue() == 0){
			everythingHasMoved = true;
		}
	
		
		
		if(currentObject == null && lastSelected != null && lastSelected.getClass() == ThiefObject.class && lastSelected.getCurrentDiceValue() != 0)
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
				Log.i("THIEF TURN STATE", " MOVE TO THIEF MOVE STATE");
				return mPlayState.thiefMoveState;
				
			}
			else if(everythingHasMoved == true){
				everythingHasMoved=false;
					return mPlayState.getCopRollDiceState();
			} 
			
			return this;
			
		} 


}