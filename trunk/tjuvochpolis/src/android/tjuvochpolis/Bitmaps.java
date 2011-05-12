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
	private static Bitmap nestSplash;
	private static Bitmap bankSplash;
	private static Bitmap copturnsplash;
	private static Bitmap getthiefsplash;
	private static Bitmap thiefturnsplash;
	private static Movie movieThiefLeft,movieThiefRight,movieThiefUp,movieThiefDown;
	private static Movie movieCopLeft,movieCopRight,movieCopUp,movieCopDown;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private static ArrayList<Movie> thiefmovies = new ArrayList<Movie>(4), copmovies = new ArrayList<Movie>(4);
	private Bitmaps() {

	}
	
	public static Bitmaps instance(Context context){
		if (bitmaps == null) {
			bitmaps = new Bitmaps();
			Resources res = context.getResources();
			setHudBottomImageCops(BitmapFactory.decodeResource(res, R.drawable.htc_police_bottom_small));
			setHudBottomImageThieves(BitmapFactory.decodeResource(res, R.drawable.htc_desire_tjuv_small));
			setHudTopImage(BitmapFactory.decodeResource(res, R.drawable.htc_top_bakgrund_256));
			setCopImage(BitmapFactory.decodeResource(res, R.drawable.police_walk_down_animate));
			setThiefImage(BitmapFactory.decodeResource(res, R.drawable.buse1_standing));
			setBankSplash(BitmapFactory.decodeResource(res, R.drawable.banksplash));
			setNestSplash(BitmapFactory.decodeResource(res, R.drawable.nestsplash));
			setCopturnsplash(BitmapFactory.decodeResource(res, R.drawable.copturnsplash));
			setGetthiefsplash(BitmapFactory.decodeResource(res, R.drawable.getthiefsplash));
			setThiefturnsplash(BitmapFactory.decodeResource(res, R.drawable.thiefturnsplash));
			//Lägg alla movies i en indexerbar array.
			movieThiefRight=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_right_animate));
			movieThiefLeft=Movie.decodeStream(context.getResources().openRawResource(R.drawable.derp));
			movieThiefUp=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_up_animate));
			movieThiefDown=Movie.decodeStream(context.getResources().openRawResource(R.drawable.buse1_walk_down_animate));
			thiefmovies.add(UP, movieThiefUp);
			thiefmovies.add(RIGHT, movieThiefRight);
			thiefmovies.add(DOWN, movieThiefDown);
			thiefmovies.add(LEFT, movieThiefLeft);
			
			movieCopRight=Movie.decodeStream(context.getResources().openRawResource(R.drawable.police_walk_right_animate));
			movieCopLeft=Movie.decodeStream(context.getResources().openRawResource(R.drawable.police_walk_left_animate));
			movieCopUp=Movie.decodeStream(context.getResources().openRawResource(R.drawable.police_walk_up_animate));
			movieCopDown=Movie.decodeStream(context.getResources().openRawResource(R.drawable.police_walk_down_animate));
			copmovies.add(UP, movieCopUp);
			copmovies.add(RIGHT, movieCopRight);
			copmovies.add(DOWN, movieCopDown);
			copmovies.add(LEFT, movieCopLeft);
			
			setBackgroundImage(BitmapFactory.decodeResource(res, R.drawable.map_1008));

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
		
	private static void setNestSplash(Bitmap nestSplash) {
		Bitmaps.nestSplash = nestSplash;
	}
	
	private static void setBankSplash(Bitmap bankSplash) {
		Bitmaps.bankSplash = bankSplash;
	}

	public Bitmap getBackgroundImage() {
		return mBackgroundImage;
	}



	
	// ANIMATION ARRAYS
	
	public Movie getThiefmovies(int index) {
		return thiefmovies.get(index);
	}
	public Movie getCopmovies(int index) {
		return copmovies.get(index);
	}
	
	public static Bitmap getBankSplash(){
		return bankSplash;
	}



	public static Bitmap getNestSplash() {
		return nestSplash;
	}

	private static void setCopturnsplash(Bitmap copturnsplash) {
		Bitmaps.copturnsplash = copturnsplash;
	}

	public static Bitmap getCopturnsplash() {
		return copturnsplash;
	}

	private static void setGetthiefsplash(Bitmap getthiefsplash) {
		Bitmaps.getthiefsplash = getthiefsplash;
	}

	public static Bitmap getGetthiefsplash() {
		return getthiefsplash;
	}

	private static void setThiefturnsplash(Bitmap thiefturnsplash) {
		Bitmaps.thiefturnsplash = thiefturnsplash;
	}

	public static Bitmap getThiefturnsplash() {
		return thiefturnsplash;
	}
}
