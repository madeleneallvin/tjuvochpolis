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

	}
	
	public void surfaceCreated(SurfaceHolder holder)
	{
		//gör det som lunarview
	}
	
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		 
	}
	
}
