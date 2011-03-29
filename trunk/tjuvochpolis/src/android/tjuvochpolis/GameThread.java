package android.tjuvochpolis;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread 
{
	
	private SurfaceHolder mSurfaceHolder;
	
	private Context mContext;
	
	private Handler mHandler;
	
	private Boolean mRun;
	
	//private GameState mGameState;

    private int mCanvasHeight = 1;

    private int mCanvasWidth = 1;
	
	public GameThread(SurfaceHolder surfaceHolder, Context context)
	{
		// Grid, Gameobjects, etc.
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		
		//mGameState = new GameState();
	}
	
	/**
     * Used to signal the thread whether it should be running or not.
     * Passing true allows the thread to run; passing false will shut it
     * down if it's already running. Calling start() after this was most
     * recently called with false will result in an immediate shutdown.
     *
     * @param b true to run, false to shut down
     */
    public void setRunning(boolean b) {
        mRun = b;
    }
	
	@Override
    public void run() {
		int color = -16711681;
        while (mRun) {
        	Canvas c = null;
        	
            try {
                c = mSurfaceHolder.lockCanvas(null);
                synchronized (mSurfaceHolder) {
                    drawScreen(c, color);
                    color--;
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }

        }
	}
	
	/* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int width, int height) {
        // synchronized to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;
        }
    }

	
	public void drawScreen(Canvas canvas, int color)
	{
		//ta bort när gamestate doDraw är aktiverat

		canvas.drawColor(color);
		//------------------------
		
		//mGameState.doDraw(canvas);
	}
	

	
}
