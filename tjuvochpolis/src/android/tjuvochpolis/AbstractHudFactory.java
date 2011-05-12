package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class AbstractHudFactory {

	protected PlayState mPs;
	
	protected Bitmap mBottomHud = null;
	protected Bitmap mButton1 = null;
	protected Bitmap mButton2 = null;
	protected Bitmap mDiceImage;
	protected ArrayList<Bitmap> mDiceSegments;
	protected Bitmap mPlayerIcon;
	protected Bitmap mBottomHudBackgroundImage;
	protected Bitmap mTopHud;
	
	protected Canvas mDiceCanvas;
	protected Canvas mBottomCanvas;
	
	protected int mScreenWidth = 0;
	protected int mScreenHeight = 0;
	
	public AbstractHudFactory(PlayState ps, Canvas c)
	{
		mScreenWidth = c.getWidth();
		mScreenHeight = c.getHeight();
		mPs = ps;
		mDiceImage = Bitmaps.instance(ps.getContext()).getHudDiceImage();
		mBottomHud = Bitmap.createBitmap(mScreenWidth, Grid.GRID_SIZE, Bitmap.Config.ARGB_8888);
		
		int wid = mDiceImage.getWidth();
		int hei = mDiceImage.getHeight();

		mDiceSegments = new ArrayList<Bitmap>();
		
		int x = 0;
		for(int i = 0; i < 6; i++)
		{
			mDiceSegments.add(Bitmap.createBitmap(mDiceImage, x, 0, 30, 30));
			x += 30;
		}
		
		mBottomCanvas = new Canvas(mBottomHud);
	}
	
	public Bitmap getBottomHud(PlayState ps, Canvas c)
	{
		mPs = ps;
		generateBottomHud();
		return mBottomHud;
	}
	
	public Bitmap getTopHud(PlayState ps, Canvas c)
	{
		mTopHud = Bitmaps.instance(ps.getContext()).getHudTopImage();
		return mTopHud;
	}
	
	public abstract void generateBottomHud();
	
}
