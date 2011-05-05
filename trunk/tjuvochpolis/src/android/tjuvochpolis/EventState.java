package android.tjuvochpolis;

import java.util.ArrayList;

import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventState extends PlayOrderState {

	public EventState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	@Override
	public void doTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public PlayOrderState getNextState() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void handleState(int frame) {
		
		Log.i("EventState", "handelState");
		
		GridNode n = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex()).getParentNode();
		//Log.i("EventState", "" + getCurrentObjectSelected());
		GameObject gm = mGameObjects.get(mObjectIndex.valueOf(getCurrentObjectSelected()).getIndex());
		Log.i("EventState", "" + n.toString());
		//gm.getParentNode().getGameStaticObject().handleEvent();
		n.getGameStaticObject().handleEvent();
		
		/*if(gm.getParentNode().getGameStaticObject() != null)
			Log.i("lulu", "");*/
		//n.getGameStaticObject().handleEvent();
		
		

	}

}
