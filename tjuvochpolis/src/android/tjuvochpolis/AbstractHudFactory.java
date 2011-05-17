package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

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
	protected Bitmap mSpriteActive;
	protected Bitmap mSpriteInactive;
	protected Bitmap mCoin;
	
	protected Canvas mDiceCanvas;
	protected Canvas mBottomCanvas;
	protected Canvas mTopCanvas;
	
	protected Paint paintText;
	protected Paint paintBigText;
	protected Paint paintWaitingLeft;
	
	protected Paint shadePaint;
	
	protected int mScreenWidth = 0;
	protected int mScreenHeight = 0;
	
	public AbstractHudFactory(PlayState ps, Canvas c)
	{
		mScreenWidth = c.getWidth();
		mScreenHeight = c.getHeight();

		shadePaint = new Paint();
		shadePaint.setARGB(180, 150, 150, 150);
		
		mPs = ps;
		mDiceImage = Bitmaps.instance(ps.getContext()).getHudDiceImage();
		mCoin = Bitmaps.instance(ps.getContext()).getHudCoin();
		
		mTopHud = Bitmap.createBitmap(mScreenWidth, Grid.GRID_SIZE, Bitmap.Config.ARGB_8888);
		mBottomHud = Bitmap.createBitmap(mScreenWidth, Grid.GRID_SIZE, Bitmap.Config.ARGB_8888);
		mBottomCanvas = new Canvas(mBottomHud);
		mTopCanvas = new Canvas(mTopHud);

		mDiceSegments = new ArrayList<Bitmap>();
		
		int x = 0;
		for(int i = 0; i < 6; i++)
		{
			mDiceSegments.add(Bitmap.createBitmap(mDiceImage, x, 0, 30, 30));
			x += 30;
		}
		
		paintText = new Paint();
		paintText.setARGB(255, 255, 255, 0);
		
		paintBigText = new Paint();
		paintBigText.setARGB(255, 255, 255, 0);
		paintBigText.setTextSize((float) (mTopCanvas.getHeight()*0.4));
		paintBigText.setTextScaleX((float)1.15);
		
		paintWaitingLeft = new Paint();
		paintWaitingLeft.setARGB(255, 255, 10, 20);
		paintWaitingLeft.setTextSize((float) (mBottomCanvas.getHeight()*0.8));
		paintWaitingLeft.setTextScaleX((float)1.25);
		
	}
	
	public Bitmap getBottomHud(PlayState ps, Canvas c)
	{
		mPs = ps;
		generateBottomHud();
		return mBottomHud;
	}
	
	public Bitmap getTopHud(PlayState ps, Canvas c)
	{
		//mTopHud = Bitmaps.instance(ps.getContext()).getHudTopImage();
		generateTopHud();
		return mTopHud;
	}
	
	public abstract void generateBottomHud();
	
	public abstract void generateTopHud();
	
}
