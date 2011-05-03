package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bitmaps {

	static Bitmaps bitmaps;
	private static Bitmap mHudBottomImageCops;
	private static Bitmap mHudBottomImageThieves;
	private static Bitmap mHudTopImage;
	private static Bitmap mCopImage;
	private static Bitmap mThiefImage;
	private static Bitmap mBackgroundImage;
	
	private Bitmaps() {

	}
	
	public static Bitmaps instance(Context context) {
		if (bitmaps == null) {
			bitmaps = new Bitmaps();
			Resources res = context.getResources();
			setHudBottomImageCops(BitmapFactory.decodeResource(res, R.drawable.htc_police_bottom_small));
			setHudBottomImageThieves(BitmapFactory.decodeResource(res, R.drawable.htc_desire_tjuv_small));
			setHudTopImage(BitmapFactory.decodeResource(res, R.drawable.htc_top_bakgrund_256));
			setCopImage(BitmapFactory.decodeResource(res, R.drawable.small_police));
			setThiefImage(BitmapFactory.decodeResource(res, R.drawable.small_thief));
			setBackgroundImage(BitmapFactory.decodeResource(res, R.drawable.map_small));       
		}
		return bitmaps;
	}

	private static void setHudBottomImageCops(Bitmap hudBottomImageCops) {
		mHudBottomImageCops = hudBottomImageCops;
	}

	public Bitmap getHudBottomImageCops() {
		return mHudBottomImageCops;
	}

	private static void setHudTopImage(Bitmap hudTopImage) {
		mHudTopImage = hudTopImage;
	}

	public Bitmap getHudTopImage() {
		return mHudTopImage;
	}

	private static void setCopImage(Bitmap copImage) {
		mCopImage = copImage;
	}

	public Bitmap getCopImage() {
		return mCopImage;
	}

	private static void setThiefImage(Bitmap thiefImage) {
		Bitmaps.mThiefImage = thiefImage;
	}

	public Bitmap getThiefImage() {
		return mThiefImage;
	}

	private static void setHudBottomImageThieves(Bitmap hudBottomImageThieves) {
		Bitmaps.mHudBottomImageThieves = hudBottomImageThieves;
	}

	public Bitmap getHudBottomImageThieves() {
		return mHudBottomImageThieves;
	}

	private static void setBackgroundImage(Bitmap mBackgroundImage) {
		Bitmaps.mBackgroundImage = mBackgroundImage;
	}

	public Bitmap getBackgroundImage() {
		return mBackgroundImage;
	}
}
