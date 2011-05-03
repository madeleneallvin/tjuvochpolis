package android.tjuvochpolis;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public interface GameState
{
	public void handleState(Canvas canvas);

	public void nextState(GameThread gt);

	public void doTouch(View v, MotionEvent event);

	public void draw(Canvas c);
}
