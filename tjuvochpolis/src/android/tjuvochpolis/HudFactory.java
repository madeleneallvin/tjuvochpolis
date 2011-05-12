package android.tjuvochpolis;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class HudFactory {

	private static Bitmap mLastHud;
	private static CopHudFactory mCopHudFactory = null;
	private static ThiefHudFactory mThiefHudFactory = null;
	private static int count = 0;
	
	private static boolean mInitiated = false;
	
	private static void initiate(PlayState ps, Canvas c)
	{
		if(!mInitiated)
		{
			mCopHudFactory = new CopHudFactory(ps, c);
			mThiefHudFactory = new ThiefHudFactory(ps, c);
			mLastHud = mCopHudFactory.getBottomHud(ps, c);
			mInitiated = true;
		}
	}
	
	public static Bitmap getBottomHud(PlayState ps, Canvas c)
	{
		initiate(ps, c);
		
		if(ps.getCurrentPlayOrderState() == ps.getCopTurnState() || ps.getCurrentPlayOrderState() == ps.getCopMoveState())
		{
			mLastHud = mCopHudFactory.getBottomHud(ps, c);
		}
		else if(ps.getCurrentPlayOrderState() == ps.getThiefTurnState() || ps.getCurrentPlayOrderState() == ps.getThiefMoveState())
		{
			mLastHud =  mThiefHudFactory.getBottomHud(ps, c);
		}
		
		return mLastHud;
	}
	
	public static Bitmap getTopHud(PlayState ps, Canvas c)
	{
		initiate(ps, c);
		
		if(ps.getCurrentPlayOrderState() == ps.getCopTurnState() || ps.getCurrentPlayOrderState() == ps.getCopMoveState())
		{
			return mCopHudFactory.getTopHud(ps, c);
		}
		else 
		{
			return mThiefHudFactory.getTopHud(ps, c);
		}

	}
	
}
