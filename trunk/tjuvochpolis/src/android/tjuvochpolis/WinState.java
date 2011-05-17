package android.tjuvochpolis;

import java.util.ArrayList;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class WinState extends PlayOrderState{

	public WinState(PlayState ps, ArrayList<GameObject> gameObjects,
			ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index) {
		super(ps, gameObjects, gameStaticObjects, grid, index);
	}

	public void doTouch(View v, MotionEvent event){
		
		if(event.getY() > PlayState.HUD_TOP_HEIGHT && event.getY() < v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT)
		{
			Intent intent = new Intent(mPlayState.getContext(), MenuActivity.class);
			mPlayState.getContext().startActivity(intent);
		}
	}
	@Override
	public PlayOrderState getNextState() {
		return this;
	}
	public void doDraw(Canvas c, float mZoom){;
		Bitmap winSplash = null; 
		if(mPlayState.calculateCopTeamMoney() > PlayState.AMOUNT_TO_WIN){
			winSplash = Bitmaps.instance(this.mPlayState.getContext()).getPoliswin();
		}
		else if(mPlayState.calculateThiefTeamMoney() > PlayState.AMOUNT_TO_WIN){
			winSplash = Bitmaps.instance(this.mPlayState.getContext()).getThiefwin();
		}
		
		Rect winRect = new Rect(0, PlayState.HUD_TOP_HEIGHT, c.getWidth(),c.getHeight()-PlayState.HUD_BOTTOM_HEIGHT);
		c.drawBitmap(winSplash, null, winRect, null);
	}
	
	@Override
	public void handleState(int frame) {
	}

}
