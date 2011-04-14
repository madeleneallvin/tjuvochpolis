package android.tjuvochpolis;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

public class GameThread extends Thread 
{
	
	private SurfaceHolder mSurfaceHolder;
	
	private Context mContext;
	
	private Handler mHandler;
	
	private long mTime;
	
	private boolean mRun;
	
	private GameState menuState;// = new MenuState();
	
	private GameState playState;// = new PlayState();
	
	private GameState currentState;// = menuState;
	
	// Eventuellt en background om thread själv ska rita bakgrunden

    private int mCanvasHeight = 1;

    private int mCanvasWidth = 1;
	
	public GameThread(SurfaceHolder surfaceHolder, Context context)
	{
		// Grid, Gameobjects, etc.
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		
		menuState = new MenuState(context);
		playState = new PlayState(context);
		currentState = playState;
		
		mTime = System.currentTimeMillis();
		
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
        setmRun(b);
    }
	
	@Override
    public void run()
	{
		
        while (ismRun())
        {
        	Canvas canvas = null;

			try {
			    canvas = mSurfaceHolder.lockCanvas(null);
			    
			    synchronized (mSurfaceHolder) {
			        
		        	currentState.handleState(canvas);
		        	currentState.nextState(this);
		            
			    }
			} 
			catch (Exception e){
				setmRun(false);
			}
			finally {
			    // do this in a finally so that if an exception is thrown
			    // during the above, we don't leave the Surface in an
			    // inconsistent state
			    if (canvas != null)
			    {
			        mSurfaceHolder.unlockCanvasAndPost(canvas);
			    }
			}
			
        }
       long frameTime = System.currentTimeMillis() - mTime;
       mTime = System.currentTimeMillis();
       int miliPerFrame = 1000 / PlayState.MAX_FPS;
       if(frameTime < miliPerFrame)
		try {
			Thread.sleep(miliPerFrame - frameTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //android.os.Process.killProcess(android.os.Process.myPid()); //kills everything
        
	}
	
	/* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int width, int height) {
        // synchronized to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;
        }
    }

	public GameState getPlayState() {
		return playState;
	}
	
	public GameState getMenuState() {
		return menuState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	void setmRun(boolean mRun) {
		this.mRun = mRun;
	}

	boolean ismRun() {
		return mRun;
	}
	

	
}
