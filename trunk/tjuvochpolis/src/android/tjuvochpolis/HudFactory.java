package android.tjuvochpolis;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class HudFactory {

	private static CopHudFactory mCopHudFactory = null;
	private static boolean mInitiated = false;
	
	public static Bitmap getBottomHud(PlayState ps, Canvas c)
	{
		if(mInitiated == false)
		{
			mCopHudFactory = new CopHudFactory(ps);
			mInitiated = true;
		}
		
		if(ps.getCurrentPlayOrderState() == ps.getCopTurnState())
		{
			return mCopHudFactory.getBottomHud(ps, c);
		}
		
		Resources res = ps.getContext().getResources();
		return BitmapFactory.decodeResource(res, R.drawable.htc_police_bottom_small);
	}
	
}
