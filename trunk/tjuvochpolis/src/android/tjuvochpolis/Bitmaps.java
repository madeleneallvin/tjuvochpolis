package android.tjuvochpolis;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;

public class Bitmaps {

	static Bitmaps bitmaps;
	
	//Hud Images
	private Bitmap mHudBottomImage;
	private Bitmap mHudBottomImageCops;
	private Bitmap mHudBottomImageThieves;
	private Bitmap mHudTopImageCops;
	private Bitmap mHudTopImageThieves;
	private Bitmap mHudBottomCopBg;
	private Bitmap mHudBottomThiefBg;
	private Bitmap mHudCopPlayerIcon;
	private Bitmap mHudThiefPlayerIcon;
	private Bitmap mHudDiceImage;
	private Bitmap mHudCoin;
	private Bitmap mHudCopSprite;
	private Bitmap mHudThiefSprite;
	
	private Bitmap copImage;
	private Bitmap thiefImage;
	private Bitmap nestSplash;
	private Bitmap bankSplash;
	private Bitmap copturnsplash;
	private Bitmap getthiefsplash;
	private Bitmap thiefturnsplash;
	private Bitmap fikaSplash;
	private Bitmap poliswin;
	private Bitmap thiefwin;
	private Bitmap thiefcapturewin;
	
	private Bitmap mBackgroundImage;
	private Movie movieThiefLeft,movieThiefRight,movieThiefUp,movieThiefDown;
	private Movie movieCopLeft,movieCopRight,movieCopUp,movieCopDown;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private ArrayList<Movie> thiefmovies = new ArrayList<Movie>(4), copmovies = new ArrayList<Movie>(4);
	
	private Bitmaps(Context context) {

		Resources res = context.getResources();
		
		setHudBottomImageCops(BitmapFactory.decodeResource(res, R.drawable.htc_police_bottom_small));
		setHudBottomImageThieves(BitmapFactory.decodeResource(res, R.drawable.htc_desire_tjuv_small));
		setHudTopImageCops(BitmapFactory.decodeResource(res, R.drawable.hud_top_cop));
		setHudTopImageThieves(BitmapFactory.decodeResource(res, R.drawable.hud_top_tjuv));
		setHudBottomCopBg(BitmapFactory.decodeResource(res, R.drawable.copbottom));
		setHudBottomThiefBg(BitmapFactory.decodeResource(res, R.drawable.htc_desire_tjuv));
		
		setHudCopSprite(BitmapFactory.decodeResource(res, R.drawable.hud_polis));
		setHudThiefSprite(BitmapFactory.decodeResource(res, R.drawable.hud_tjuv));
		
		setHudCopPlayerIcon(BitmapFactory.decodeResource(res, R.drawable.hud_cop_face));
		setHudThiefPlayerIcon(BitmapFactory.decodeResource(res, R.drawable.hud_tjuv_face));
		setHudDiceImage(BitmapFactory.decodeResource(res, R.drawable.dices));
		setHudCoin(BitmapFactory.decodeResource(res, R.drawable.hud_coin));
		
		setCopImage(BitmapFactory.decodeResource(res, R.drawable.polis_standing));
		setThiefImage(BitmapFactory.decodeResource(res, R.drawable.buse1_standing));
		
		setBankSplash(BitmapFactory.decodeResource(res, R.drawable.banksplash));
		setNestSplash(BitmapFactory.decodeResource(res, R.drawable.nestsplash));
		setCopturnsplash(BitmapFactory.decodeResource(res, R.drawable.copturnsplash));
		setGetthiefsplash(BitmapFactory.decodeResource(res, R.drawable.getthiefsplash));
		setThiefturnsplash(BitmapFactory.decodeResource(res, R.drawable.thiefturnsplash));
		setHudBottomImage(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
		setFikaSplash(BitmapFactory.decodeResource(res, R.drawable.fika));
		setPoliswin(BitmapFactory.decodeResource(res, R.drawable.poliswin));
		setThiefwin(BitmapFactory.decodeResource(res, R.drawable.thiefwin));
		setThiefcapturewin(BitmapFactory.decodeResource(res, R.drawable.thiefcapturewin));
		
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
	
	public static Bitmaps instance(Context context){
		if (bitmaps == null) {
			bitmaps = new Bitmaps(context);
     
		}
		return bitmaps;
	}

	private void setHudBottomImageCops(Bitmap hudBottomImageCops) {
		this.mHudBottomImageCops = hudBottomImageCops;
	}

	public Bitmap getHudBottomImageCops() {
		return mHudBottomImageCops;
	}

	private void setHudTopImageCops(Bitmap hudTopImage) {
		this.mHudTopImageCops = hudTopImage;
	}

	public Bitmap getHudTopImageCops() {
		return mHudTopImageCops;
	}

	private void setHudTopImageThieves(Bitmap hudTopImage) {
		this.mHudTopImageThieves = hudTopImage;
	}

	public Bitmap getHudTopImageThieves() {
		return mHudTopImageThieves;
	}
	
	private void setHudBottomImageThieves(Bitmap hudBottomImageThieves) {
		this.mHudBottomImageThieves = hudBottomImageThieves;
	}

	public Bitmap getHudBottomImageThieves() {
		return mHudBottomImageThieves;
	}

	private void setBackgroundImage(Bitmap mBackgroundImage) {
		this.mBackgroundImage = mBackgroundImage;
	}
	
	private void setNestSplash(Bitmap nestSplash) {
		this.nestSplash = nestSplash;
	}
	
	private void setBankSplash(Bitmap bankSplash) {
		this.bankSplash = bankSplash;
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

	private void setHudBottomCopBg(Bitmap mHudBottomCopBg) {
		this.mHudBottomCopBg = mHudBottomCopBg;
	}

	public Bitmap getHudBottomCopBg() {
		return mHudBottomCopBg;
	}

	public void setHudBottomThiefBg(Bitmap mHudBottomThiefBg) {
		this.mHudBottomThiefBg = mHudBottomThiefBg;
	}

	public Bitmap getHudBottomThiefBg() {
		return mHudBottomThiefBg;
	}

	public void setHudCopPlayerIcon(Bitmap mHudCopPlayerIcon) {
		this.mHudCopPlayerIcon = mHudCopPlayerIcon;
	}

	public Bitmap getHudCopPlayerIcon() {
		return mHudCopPlayerIcon;
	}

	public void setHudThiefPlayerIcon(Bitmap mHudThiefPlayerIcon) {
		this.mHudThiefPlayerIcon = mHudThiefPlayerIcon;
	}

	public Bitmap getHudThiefPlayerIcon() {
		return mHudThiefPlayerIcon;
	}

	public void setHudDiceImage(Bitmap mHudDiceImage) {
		this.mHudDiceImage = mHudDiceImage;
	}

	public Bitmap getHudDiceImage() {
		return mHudDiceImage;
	}
	
	public void setHudCopSprite(Bitmap mHudCopSprite) {
		this.mHudCopSprite = mHudCopSprite;
	}

	public Bitmap getHudCopSprite() {
		return mHudCopSprite;
	}
	
	public void setHudThiefSprite(Bitmap mHudThiefSprite) {
		this.mHudThiefSprite = mHudThiefSprite;
	}

	public Bitmap getHudThiefSprite() {
		return mHudThiefSprite;
	}
	
	public  Bitmap getBankSplash(){
		return bankSplash;
	}

	public Bitmap getNestSplash() {
		return nestSplash;
	}

	private void setCopturnsplash(Bitmap copturnsplash) {
		this.copturnsplash = copturnsplash;
	}

	public Bitmap getCopturnsplash() {
		return copturnsplash;
	}

	private void setGetthiefsplash(Bitmap getthiefsplash) {
		this.getthiefsplash = getthiefsplash;
	}

	public Bitmap getGetthiefsplash() {
		return getthiefsplash;
	}

	private void setThiefturnsplash(Bitmap thiefturnsplash) {
		this.thiefturnsplash = thiefturnsplash;
	}

	public Bitmap getThiefturnsplash() {
		return thiefturnsplash;
	}

	public void setHudBottomImage(Bitmap mHudBottomImage) {
		this.mHudBottomImage = mHudBottomImage;
	}

	public Bitmap getHudBottomImage() {
		return mHudBottomImage;
	}
	
	private void setFikaSplash(Bitmap fika) {
		this.fikaSplash = fika;
	}

	public Bitmap getFikaSplash() {
		return fikaSplash;
	}

	private void setPoliswin(Bitmap poliswin) {
		this.poliswin = poliswin;
	}

	public Bitmap getPoliswin() {
		return poliswin;
	}

	private void setThiefwin(Bitmap thiefwin) {
		this.thiefwin = thiefwin;
	}

	public Bitmap getThiefwin() {
		return thiefwin;
	}

	public void setHudCoin(Bitmap mHudCoin) {
		this.mHudCoin = mHudCoin;
	}

	public Bitmap getHudCoin() {
		return mHudCoin;
	}

	public void setCopImage(Bitmap copImage) {
		this.copImage = copImage;
	}

	public Bitmap getCopImage() {
		return copImage;
	}

	public void setThiefImage(Bitmap thiefImage) {
		this.thiefImage = thiefImage;
	}

	public Bitmap getThiefImage() {
		return thiefImage;
	}

	private void setThiefcapturewin(Bitmap thiefcapturewin) {
		this.thiefcapturewin = thiefcapturewin;
	}

	public Bitmap getThiefcapturewin() {
		return thiefcapturewin;
	}
}
