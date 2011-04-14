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

		interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	
	}
	
	public PlayOrderState getNextState()
	{	
		
		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving)
	
		{
			return this;
		}
		
		
		else
		{ Log.i("COP MOVE STATE", "MOVE TO COP TURN STATE");
			return mPlayState.getCopTurnState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		
	}

}
