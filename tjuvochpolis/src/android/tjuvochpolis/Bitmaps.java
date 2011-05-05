package android.tjuvochpolis;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;

public class Bitmaps {

	static Bitmaps bitmaps;
	private static Bitmap mHudBottomImageCops;
	private static Bitmap mHudBottomImageThieves;
	private static Bitmap mHudTopImage;
	private static Bitmap mCopImage;
	private static Bitmap mThiefImage;
	private static Bitmap mBackgroundImage;
	private static Movie movieThiefLeft,movieThiefRight,movieThiefUp,movieThiefDown;
	private static Movie movieCopLeft,movieCopRight,movieCopUp,movieCopDown;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private static ArrayList<Movie> thiefmovies = new ArrayList<Movie>(4);
	private Bitmaps() {

	}
	
	public static Bitmaps instance(Context context){
		if (bitmaps == null) {
			bitmaps = new Bitmaps();
			Resources res = context.getResources();
			setHudBottomImageCops(BitmapFactory.decodeResource(res, R.drawable.htc_police_bottom_small));
			setHudBottomImageThieves(BitmapFactory.decodeResource(res, R.drawable.htc_desire_tjuv_small));
			setHudTopImage(BitmapFactory.decodeResource(res, R.drawable.htc_top_bakgrund_256));
			setCopImage(BitmapFactory.decodeResource(res, R.drawable.small_police));
			setThiefImage(BitmapFactory.decodeResource(res, R.drawable.buse1_standing));
			
			//Lägg alla movies i en indexerbar array.
			movieThiefRight=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_right_animate));
			movieThiefLeft=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_left_animate));
			movieThiefUp=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_up_animate));
			movieThiefDown=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_down_animate));
			thiefmovies.add(UP, movieThiefUp);
			thiefmovies.add(RIGHT, movieThiefRight);
			thiefmovies.add(DOWN, movieThiefDown);
			thiefmovies.add(LEFT, movieThiefLeft);
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



	
	// ANIMATION ARRAYS
	
	public Movie getThiefmovies(int index) {
		return thiefmovies.get(index);
	}
}
