package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class PlayOrderState {
	

	//CopObject cop;
	//ThiefObject thief;
	protected ArrayList<GameObject> mGameObjects;
	protected ArrayList<GameStaticObject> mGameStaticObjects;
//	
	protected Grid mGrid;
	protected PlayState mPlayState;
	protected PlayOrderState mNextState;
	protected float mAnimationStep = 15;
	protected int mCurrentAnimationStep = (int) mAnimationStep + 1;
	private String currentObjectSelected;
	
	
	public PlayOrderState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid){
		
		this.mPlayState = ps;
		this.mGameObjects = gameObjects;
		this.mGameStaticObjects = gameStaticObjects;
		this.mGrid = grid;
		
	}
	/*
	public boolean canGoTo(int row, int col){
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

	//public abstract void doTouch(View v, MotionEvent event);
	
	public abstract PlayOrderState getNextState();
	
	public void doDraw(Canvas c, float mZoom)
	{
		return;
	}
	
	
	protected void interpolatedMove(GameObject go, int frame)
	{ 
		mCurrentAnimationStep++;
		
		if(mCurrentAnimationStep <= mAnimationStep)
		{ //int tempX = (go.getMovePath().get(go.getCurrentPathPosition())).getNodeX();
		 //int tempY = go.getMovePath().get(go.getCurrentPathPosition()).getNodeY();
		 //int tempXnext = go.getMovePath().get(go.getCurrentPathPosition()+1).getNodeX();
		 //int tempYnext = go.getMovePath().get(go.getCurrentPathPosition()+1).getNodeY();
			//Log.i("drawXpos:", "" + interpolate(go.getParentNode().getNodeX(), go.moveToColCoordinate, mCurrentAnimationStep));
			//Log.i("x:", "" + go.getParentNode().getNodeX() + "  " + go.moveToColCoordinate);
		go.setDrawXPos(interpolate(go.getParentNode().getNodeX(), go.moveToColCoordinate, mCurrentAnimationStep));
	//		go.setDrawXPos(interpolate(tempX, tempXnext,mCurrentAnimationStep) );
			go.setDrawYPos(interpolate(go.getParentNode().getNodeY(), go.moveToRowCoordinate, mCurrentAnimationStep));
		//	go.setDrawYPos(interpolate(tempY, tempYnext,mCurrentAnimationStep));
		}
		else
		{
			
			mCurrentAnimationStep = 0;
			ArrayList<GridNode> path = go.getMovePath();
			
			//DET SISTA ELSE-SATSEN GÖR
			if( path.size() - go.getCurrentPathPosition() < 2)
			{
				go.isMoving = false;
				mCurrentAnimationStep = (int) mAnimationStep + 1; //återställer animationstep så vi har rätt defaultvärde till nästa objekt
				go.setParentNode(path.get(path.size()-1));
				go.setCurrentPathPosition(0);
				
			}
			else{
				GridNode moveToNode = path.get(go.getCurrentPathPosition() + 1); //detta kommer inte att ske om pathsize är mindre än 2.
				
				go.moveToColCoordinate = moveToNode.getNodeX();
				go.moveToRowCoordinate = moveToNode.getNodeY();
				
				//MÅSTE FIXAS!!! Om objektet går över ett annat länkas det bort... 
				//se till att kolla ifall det redan finns ett, spara det och återställ kopplingen efter förflyttningen
				
				if(go.getCurrentPathPosition() != 0)
				{
					go.getParentNode().setGameObject(null);
					moveToNode.setGameObject(go);
				}
				go.setParentNode(path.get(go.getCurrentPathPosition()));
			}
			go.setCurrentPathPosition(go.getCurrentPathPosition() + 1);
		}

	}
	
	private float interpolate(int gridStart, int gridEnd, int frame)
	{
		
		return ((float)gridStart*Grid.GRID_SIZE) + ((float) gridEnd*Grid.GRID_SIZE - gridStart*Grid.GRID_SIZE) * ((float) mCurrentAnimationStep/mAnimationStep);
	}

	public void setCurrentObjectSelected(String currentObjectSelected) {
		this.currentObjectSelected = currentObjectSelected;
	}

	public String getCurrentObjectSelected() {
		return currentObjectSelected;
	}
	

}

	
