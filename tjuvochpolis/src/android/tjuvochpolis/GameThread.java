package android.tjuvochpolis;
import android.content.Context;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread 
{
	
	private SurfaceHolder mSurfaceHolder;
	
	private Context mContext;
	
	private Handler mHandler;
	
	private Boolean mRun;
	
	// Eventuellt en background om thread själv ska rita bakgrunden
	
	public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
	{
		// Grid, Gameobjects, etc.
	}
	
	@Override
    public void run() {
        while (mRun) {
        	//do something
        }
	}
	

	
	public void drawScreen()
	{
		
	}
	
}
