package android.tjuvochpolis;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.tjuvochpolis.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback 
{
	
	private Context mContext;
	
	private GameThread thread;

	private OnTouchListener mListener = new OnTouchListener() {
	   
		public boolean onTouch(View v, MotionEvent event) {
			thread.getCurrentState().doTouch(v, event);
			return true;
		}		
	};
	
	public GameView(Context context, AttributeSet atrbs)
	{
		super(context, atrbs);
		
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        thread = new GameThread(holder, context);
        
        setFocusable(true);
        this.setOnTouchListener(mListener);

	}
	
	public void surfaceCreated(SurfaceHolder holder)
	{
		// start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
		if (thread.getState() == Thread.State.NEW)
		{
			thread.setRunning(true);
	        thread.start();
		}
		else
		{
			thread = new GameThread(holder, this.getContext());
			thread.setRunning(true);
	        thread.start();
		}
		
	}
	
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
		
		thread.saveState(mContext);
		
        boolean retry = true;
        thread.setRunning(false);
        
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		thread.setSurfaceSize(width, height);

	}
	
	
	public GameThread getThread()
	{
		return thread;
	}
	
}
