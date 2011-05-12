package android.tjuvochpolis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class CopHudFactory extends AbstractHudFactory {
	
	public CopHudFactory(PlayState ps)
	{
		super(ps);
		mBottomHudBackgroundImage = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.copbottom);
	}
	
	public void generateBottomHud()
	{
		Canvas canvas = new Canvas(mBottomHud);
		
		canvas.drawBitmap(mBottomHudBackgroundImage, 0, 0, null);
		canvas.drawBitmap(drawCop((CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP1)), 0, 0, null);
		canvas.drawBitmap(drawCop((CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP2)), 96, 0, null);
		//canvas.drawBitmap(drawCop((CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP3)), 96, 0, null);
		
	}
	
	public Bitmap drawCop(CopObject cop)
	{
		Bitmap out = Bitmap.createBitmap(96, 48, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(out);
		
		canvas.drawARGB(1, 0, 10, 190);
		Bitmap diceImage = getDiceImage(cop.getCurrentDiceValue());
		canvas.drawBitmap(diceImage, 60, 9, null);
		return out;
	}
}
