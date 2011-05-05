package android.tjuvochpolis;

import java.util.ArrayList;
import android.tjuvochpolis.PlayState.mObjectIndex;
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
		interpolatedMove(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()), frame);
	}

	public PlayOrderState getNextState()
	{	

		if(mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).isMoving){
			return this;
		}
		else{ 
			/*GridNode n2 = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode();
			Log.i("CopMoveState", "" + n2.toString());
			if(n2.getType() == GridNode.POLICE_STATION)
			{
				Log.i("CopMoveState", "ITS a POLICE_STATION");
				return mPlayState.getEventState();
			}
			else if(n2.getType() == GridNode.TELEGRAPH)
			{
				Log.i("CopMoveState", "ITS a TELEGRAPH");
				return mPlayState.getEventState();
			}
			*/
			return mPlayState.getCopTurnState();
		}
	}

	@Override
	public void doTouch(View v, MotionEvent event) {

	}

}
