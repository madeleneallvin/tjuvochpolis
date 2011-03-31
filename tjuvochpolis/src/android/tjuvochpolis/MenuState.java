package android.tjuvochpolis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class MenuState implements GameState 
{
	int color = Color.BLACK;
	int nextState = 0;
	Layout layout;
	Paint paint;
	Rect rect;
	Rect rect2;
	
	public MenuState(Context context)
	{
		//layout = (Layout)context.getResources().getLayout(R.layout.menustate);
		paint = new Paint();
		rect = new Rect();
		rect2 = new Rect();
		rect.set(10, 10, 100, 50);
		rect2.set(10, 70, 100, 110);
		paint.setColor(Color.WHITE);
	}
	
	public void handleState(Canvas canvas)
	{
		
		
		//color--;
		draw(canvas);
		
	}
	

	
	public void nextState(GameThread gt)
	{
		if(nextState == 1){
			gt.setCurrentState(gt.getPlayState());
			
		}
		
		if(nextState == 2){
			Throwable throwable = new Throwable();
			
			//gt.setmRun(false);
			//gt.stop(); // temporär close
			android.os.Process.killProcess(android.os.Process.myPid()); //kills everything
			
		}
	}
	
	
	private void draw(Canvas c)
	{
		c.drawColor(color);
		c.drawRect(rect, paint);
		c.drawRect(rect2, paint);
		
	}
	//:
	public void doTouch(View v, MotionEvent event)
	{
		//if(event.getX());
		if(event.getX() > rect.left && event.getX() < rect.right
				&& event.getY() > rect.top && event.getY() < rect.bottom) {
			nextState = 1;
			
			
		}
		
		if(event.getX() > rect2.left && event.getX() < rect2.right
				&& event.getY() > rect2.top && event.getY() < rect2.bottom) {
			nextState = 2;
		}
	}
	
}
