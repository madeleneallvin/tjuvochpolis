package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
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
				
				CopObject currentCop = ((CopObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()));
				ThiefObject currentThief = currentCop.getThiefCaught();
				
				int policeCol, policeRow, thiefCol, thiefRow;
				//hårdkodade positioner för tjuvarnas och polisernas platser i fängelset och polisstationen :P
				if(currentCop.name.equalsIgnoreCase("cop1")){
					policeCol = 11;
					policeRow = 9;
				}else{
					policeCol = 11;
					policeRow = 8;
				}
				if(currentThief.name.equalsIgnoreCase("thief1")){
					thiefCol = 9;
					thiefRow = 8;
				}else{
					thiefCol = 9;
					thiefRow = 9;
				}
				
				currentCop.transportToJail(policeRow, policeCol, mGrid);
				currentThief.transportToJail(thiefRow,thiefCol, mGrid);
				
				currentCop.setObjectMoney(currentThief.getObjectMoney()/2);
				currentThief.setObjectMoney(0);
				
				int wait = Dice.getDice().rollDice();
				currentCop.setWaitingLeft(wait-1); // -1 är för att polisen ska få komma ut en omgång före tjuven
				currentThief.setWaitingLeft(wait);
				Log.i("wait...", ""+wait);
				
				//currentCop.setDrawXPos(mGrid.GRID_SIZE*policeCol); currentCop.setDrawYPos(mGrid.GRID_SIZE*policeRow);
				//currentThief.setDrawXPos(mGrid.GRID_SIZE*8); currentThief.setDrawYPos(mGrid.GRID_SIZE*8);
				
				Log.i("cop name", currentCop.name);
				Log.i("thief name", currentThief.name);
				
				//reset, låt detta stå efter alla ställen vi försöker accessa aktuell tjuv och polis
				currentThief.setCaught(false);
				currentCop.setThiefCaught(null);
				mPlayState.mPreviousState = mPlayState.getCopTurnState();
				return mPlayState.getEventState();
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

	//@Override
	public void doTouch(View v, MotionEvent event, Context context) {

	}

}
