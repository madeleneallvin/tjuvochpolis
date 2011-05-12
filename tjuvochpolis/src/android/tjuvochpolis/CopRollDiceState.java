package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CopRollDiceState extends PlayOrderState {

	private boolean oneTimeShot = true;
	public CopRollDiceState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
		this.mNextState = this;
	}
	
	

	@Override
	public void doTouch(View v, MotionEvent event) {
		this.mNextState = mPlayState.copTurnState;
	}

	public void doDraw(Canvas c, float mZoom){
	
	if (oneTimeShot = true){
		
		drawSplashScreen(c , mPlayState.getContext());
		
		oneTimeShot = false;
	}
	
	}

	@Override
	public void handleState(int frame) {
		CopObject cop1 = (CopObject) this.mGameObjects.get(mObjectIndex.COP1.getIndex());
		CopObject cop2 = (CopObject) this.mGameObjects.get(mObjectIndex.COP2.getIndex());
		CopObject cop3 = (CopObject) this.mGameObjects.get(mObjectIndex.COP3.getIndex());
		
		
		//Slå tärning för poliserna.
		if(cop1.getWaitingLeft() != 0){
			cop1.setCurrentDiceValue(0);
			cop1.setWaitingLeft(cop1.getWaitingLeft()-1);
			Log.i("waiting left:", ""+cop1.getWaitingLeft());
		}else{
			cop1.setRolledDiceValue(Dice.getDice().rollDice());
		}
		
		if(cop2.getWaitingLeft() != 0){
			cop2.setCurrentDiceValue(0);
			cop2.setWaitingLeft(cop2.getWaitingLeft()-1);
			Log.i("waiting left:", ""+cop2.getWaitingLeft());
		}else{
			cop2.setRolledDiceValue(Dice.getDice().rollDice());
		}
		
		if(cop3.getWaitingLeft() != 0){
			cop3.setCurrentDiceValue(0);
			cop3.setWaitingLeft(cop3.getWaitingLeft()-1);
			Log.i("waiting left:", ""+cop3.getWaitingLeft());
		}else{
			cop3.setRolledDiceValue(Dice.getDice().rollDice());
		}
		
		//Calculate the nodeWalker
		cop1.doNodeWalker(cop1.getParentNode(), cop1.getParentNode(), cop1.getCurrentDiceValue());
		cop2.doNodeWalker(cop2.getParentNode(), cop2.getParentNode(), cop2.getCurrentDiceValue());
		cop3.doNodeWalker(cop3.getParentNode(), cop3.getParentNode(), cop3.getCurrentDiceValue());
		
		//Change state
		
	}
	
public void drawSplashScreen(Canvas c, Context context) {

		Bitmap bankSplash = Bitmaps.instance(context).getCopturnsplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect copTurnRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(bankSplash, null, copTurnRect, null);

	}

	@Override
	public PlayOrderState getNextState() {
		return this.mNextState;
	}

}
