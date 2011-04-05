package android.tjuvochpolis;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class PlayOrderState {
	PlayState ps;
	CopObject cop;
	ThiefObject thief;
	Grid grid;
	PlayOrderState nextState;
	
	protected float mAnimationStep = 25;
	protected int mCurrentAnimationStep = 0;
	
	public PlayOrderState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		this.cop = cop;
		this.thief = thief;
		this.grid = grid;
		this.ps = ps;
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
	
	protected void interpolatedMove(GameObject go, int frame) {
		
		mCurrentAnimationStep++;
		if(mCurrentAnimationStep <= mAnimationStep)
		{
			go.mDrawXPos = interpolate(go.parentNode.getNodeX(), go.moveToColCoordinate, mCurrentAnimationStep);
			go.mDrawYPos = interpolate(go.parentNode.getNodeY(), go.moveToRowCoordinate, mCurrentAnimationStep);
		}
		else
		{
			go.isMoving = false;
			//int column = (int)Math.floor(colCoordinate/48); //30 är "lagom" storlek för punkterna som ritas ut
			//int row = (int)Math.floor(rowCoordinate/48);
			go.moveTo(this.grid.gridArray[go.moveToRowCoordinate][go.moveToColCoordinate]);
		}

	}
	
	private float interpolate(int gridStart, int gridEnd, int frame)
	{
		return ((float)gridStart*Grid.GRID_SIZE) + ((float) gridEnd*Grid.GRID_SIZE - gridStart*Grid.GRID_SIZE) * (mCurrentAnimationStep/mAnimationStep);
	}
}
