package android.tjuvochpolis;

import java.util.ArrayList;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopMoveState extends PlayOrderState {

	float x = 120;
	float y = 120;


	public CopMoveState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index){
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	public void handleState(int frame)
	{
		this.interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	}

	public PlayOrderState getNextState()
	{	

		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving){
			return this;
		}
		else{
			if(((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).getThiefCaught() != null){
				
				//FIXA TILL SÅ ATT DE RITAS UT IGEN PÅ SINA NYA PLATSER!!!
				
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).transportToJail(8, 8, mGrid);
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).getThiefCaught().transportToJail(8,11, mGrid);
				
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).setDrawXPos(mGrid.GRID_SIZE*8);
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).setDrawYPos(mGrid.GRID_SIZE*8);
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).getThiefCaught().setDrawXPos(mGrid.GRID_SIZE*11);
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).getThiefCaught().setDrawYPos(mGrid.GRID_SIZE*8);
				
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).getThiefCaught().setCaught(false);
				((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex())).setThiefCaught(null);
				
				
				
				//mPlayState.mPreviousState = mPlayState.getCopTurnState();
				//return mPlayState.getEventState();
			}
			GridNode currentNode = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode();
			
			// Depending of the type the event state is activated
			if(currentNode.getType() == GridNode.POLICE_STATION)
			{
				mPlayState.mPreviousState = mPlayState.getCopTurnState();
				return mPlayState.getEventState();
			}
			/* SPARA UTIFALL VI SKULLE VILJA HA SENARE
			else if(currentNode.getType() == GridNode.TELEGRAPH)
			{
				mPlayState.mPreviousState = mPlayState.getCopTurnState();
				//return mPlayState.getEventState();
			}
			*/
			return mPlayState.getCopTurnState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

	}

}
