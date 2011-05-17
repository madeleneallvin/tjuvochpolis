package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThiefMoveState extends PlayOrderState {

	float x = 120;
	float y = 120;

	public ThiefMoveState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	public void handleState(int frame) {
		interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	}

	public PlayOrderState getNextState() {	
		
		GridNode currentNode = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode();
		
		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving) {
			Log.i("thiefMoveState", "return to thiefMoveState");
			return this;
		}
	//else {
			ThiefObject currentThief = ((ThiefObject)mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()));
			/*	
			if(currentThief.isCaught()){
				
				int thiefCol, thiefRow;
			
				if(currentThief.name.equalsIgnoreCase("thief1")){
					thiefCol = 9;
					thiefRow = 8;
				}else if(currentThief.name.equalsIgnoreCase("thief2")){
					thiefCol = 8;
					thiefRow = 9;
				}else{
					thiefCol = 9;
					thiefRow = 9;
				}
				
				
				currentThief.transportToJail(thiefRow,thiefCol, mGrid);
				currentThief.setObjectMoney(0);
				int wait = Dice.getDice().rollDice();
				currentThief.setWaitingLeft(wait);
								
				//reset, låt detta stå efter alla ställen vi försöker accessa aktuell tjuv och polis
				currentThief.setCaught(false);
				mPlayState.mPreviousState = mPlayState.getThiefTurnState();
				
			//	return mPlayState.getThiefTurnState();
			}
			*/
			
			if(currentNode.getType() == GridNode.THIEF_NEST || currentNode.getType() == GridNode.BANK)
			{
				mPlayState.mPreviousState = mPlayState.getThiefTurnState();
				Log.i("thiefMoveState", "return to getEventState");
				return mPlayState.getEventState();
			}
			
			Log.i("thiefMoveState", "return to thiefTurnState");
			return mPlayState.getThiefTurnState();
	//	}	
	}


	@Override
	public void doTouch(View v, MotionEvent event) {
	}
}