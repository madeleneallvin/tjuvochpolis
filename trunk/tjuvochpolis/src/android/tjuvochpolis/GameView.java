package android.tjuvochpolis;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.tjuvochpolis.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback 
{
	
	private Context mContext;
	
	private GameThread thread;

	
	public GameView(Context context, AttributeSet atrbs)
	{
		super(context, atrbs);
		
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        thread = new GameThread(holder, context);
        
        setFocusable(true);

	}
	
	public void surfaceCreated(SurfaceHolder holder)
	{
		// start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
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
