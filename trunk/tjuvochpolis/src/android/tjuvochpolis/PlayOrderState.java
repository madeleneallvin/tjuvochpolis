package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class PlayOrderState {
	

	//CopObject cop;
	//ThiefObject thief;
	protected ArrayList<GameObject> mGameObjects;
	
	protected Grid mGrid;
	protected PlayState mPlayState;
	protected PlayOrderState mNextState;
	protected float mAnimationStep = 15;
	protected int mCurrentAnimationStep = (int) mAnimationStep + 1;
	private String currentObjectSelected;
	
	public PlayOrderState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid){
		
		this.mPlayState = ps;
		this.mGameObjects = gameObjects;
		this.mGrid = grid;
		
	}
	
	/*public boolean canGoTo(int row, int col){
		if(grid.gridArray[row][col].getType() == GridNode.STREET)
		{			
			return true;
		}
		else if (grid.gridArray[row][col].getType() == GridNode.POLICE_STATION){
			return true;
		}
		else if (grid.gridArray[row][col].getType() == GridNode.TELEGRAPH){
			return true;
		}
		
		return false;
	}
	*/

	public abstract void handleState(int frame);
	
	public abstract void doTouch(View v, MotionEvent event);
	
	public abstract PlayOrderState getNextState();
	
	public void doDraw(Canvas c)
	{
		return;
	}
	
	protected void interpolatedMove(GameObject go, int frame)
	{
		mCurrentAnimationStep++;
		if(mCurrentAnimationStep <= mAnimationStep)
		{
			//Log.i("x:", "" + go.getParentNode().getNodeX() + "  " + go.moveToColCoordinate);
			go.setDrawXPos(interpolate(go.getParentNode().getNodeX(), go.moveToColCoordinate, mCurrentAnimationStep));
			go.setDrawYPos(interpolate(go.getParentNode().getNodeY(), go.moveToRowCoordinate, mCurrentAnimationStep));
		}
		else
		{
			mCurrentAnimationStep = 0;
			ArrayList<GridNode> path = go.getMovePath();
			GridNode moveToNode = path.get(1);
			
		//	Log.i("Path", "" + path.toString());
			
			if(moveToNode != null)
			{
				go.moveToColCoordinate = moveToNode.getNodeX();
				go.moveToRowCoordinate = moveToNode.getNodeY();
			}
			else
			{
				go.isMoving = false;
			}
			go.setParentNode(path.get(0));
			path.remove(go.getParentNode());
			//go.moveTo(moveToNode);
			
			//int column = (int)Math.floor(colCoordinate/48); //30 är "lagom" storlek för punkterna som ritas ut
			//int row = (int)Math.floor(rowCoordinate/48);
			//go.moveTo(this.mGrid.mGridArray[go.moveToRowCoordinate][go.moveToColCoordinate]);
		}

	}
	
	private float interpolate(int gridStart, int gridEnd, int frame)
	{
		return ((float)gridStart*Grid.GRID_SIZE) + ((float) gridEnd*Grid.GRID_SIZE - gridStart*Grid.GRID_SIZE) * (mCurrentAnimationStep/mAnimationStep);
	}

	public void setCurrentObjectSelected(String currentObjectSelected) {
		this.currentObjectSelected = currentObjectSelected;
	}

	public String getCurrentObjectSelected() {
		return currentObjectSelected;
	}
	

}

	
