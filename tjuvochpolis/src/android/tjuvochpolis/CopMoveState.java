package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.tjuvochpolis.PlayState.mObjectStaticIndex;
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
				//h�rdkodade positioner f�r tjuvarnas och polisernas platser i f�ngelset och polisstationen :P
				if(currentCop.name.equalsIgnoreCase("cop1")){
					policeCol = 11;
					policeRow = 8;
				}else if(currentCop.name.equalsIgnoreCase("cop2")){
					policeCol = 11;
					policeRow = 9;
				}else{
					policeCol = 12;
					policeRow = 9;
				}
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
				
				currentCop.transportToJail(policeRow, policeCol, mGrid);
				currentThief.transportToJail(thiefRow,thiefCol, mGrid);
				
				// Lagrar polisens pengar p� polishus 1
				this.mGameStaticObjects.get(mObjectStaticIndex.POLICESTATION1.getIndex()).setObjectMoney(this.mGameStaticObjects.get(mObjectStaticIndex.POLICESTATION1.getIndex()).getObjectMoney() + currentThief.getObjectMoney()/2);
				currentThief.setObjectMoney(0);
				
				// Check if the cops have won
				if(mPlayState.calculateCopTeamMoney() >= mPlayState.AMOUNT_TO_WIN){
					return mPlayState.getWinState();
				}
				// Check if all thiefs are captured
				else if(mPlayState.allThiefsCaptured() == true){
					return mPlayState.getWinState();
				}
				
				int wait = Dice.getDice().rollDice();
				currentCop.setWaitingLeft(wait-1); // -1 �r f�r att polisen ska f� komma ut en omg�ng f�re tjuven
				currentThief.setWaitingLeft(wait);
				
				//reset, l�t detta st� efter alla st�llen vi f�rs�ker accessa aktuell tjuv och polis
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

			return mPlayState.getCopTurnState();
		}
	}

	//@Override
	public void doTouch(View v, MotionEvent event, Context context) {

	}

}
