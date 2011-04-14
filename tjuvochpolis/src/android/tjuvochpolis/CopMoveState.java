package android.tjuvochpolis;

import java.util.ArrayList;
import java.util.Currency;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopMoveState extends PlayOrderState {
	
	float x = 120;
	float y = 120;
	
	
	public CopMoveState(PlayState ps, ArrayList<GameObject> gameObjects, Grid grid){
		super(ps, gameObjects , grid);
	}
	
	public void handleState(int frame)
	{
		Log.i("SELECTED DEUCE","" + getCurrentObjectSelected());
		Log.i("index value","" + mObjectIndex.valueOf(getCurrentObjectSelected()));
		
		Log.i("index value index","" + mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex());
		interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	//	interpolatedMove(mGameObjects.get(mObjectIndex.COP1.getIndex()), frame);
	}
	
	public PlayOrderState getNextState()
	{	
		
		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving)
		//	if(mGameObjects.get(mObjectIndex.COP1.getIndex()).isMoving)
		{
			return this;
		}
		else
		{
			return mPlayState.getThiefRollDiceState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
	}

}
