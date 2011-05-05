package android.tjuvochpolis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class AbstractHudFactory {

	protected PlayState mPs;
	
	protected Bitmap mBottomHud = null;
	protected Bitmap mButton1 = null;
	protected Bitmap mButton2 = null;
	protected Bitmap mDiceImage;
	protected Bitmap mBottomHudBackgroundImage;
	
	protected int mScreenWidth = 0;
	protected int mScreenHeight = 0;
	
	public AbstractHudFactory(PlayState ps)
	{
		mPs = ps;
		mDiceImage = BitmapFactory.decodeResource(mPs.getContext().getResources(), R.drawable.dices);
	}
	
	public Bitmap getBottomHud(PlayState ps, Canvas c)
	{
		mScreenWidth = c.getWidth();
		mScreenHeight = c.getHeight();
		mPs = ps;
		
		mBottomHud = Bitmap.createBitmap(mScreenWidth, Grid.GRID_SIZE, Bitmap.Config.ARGB_8888);
		
		generateBottomHud();
		
		return mBottomHud;
	}
	
	public abstract void generateBottomHud();
	
	protected Bitmap getDiceImage(int val)
	{
		int hello = mDiceImage.getWidth();
		
		//int x = (val-1) * 30 + 1;
		int x = 1;
		int y = 1;
		int width = 10;
		int height = 10;
		
		Bitmap newtmp = Bitmap.createBitmap(mDiceImage, x, y, width, height);
		return newtmp;
		
	}
}
