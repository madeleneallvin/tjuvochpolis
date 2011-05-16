package android.tjuvochpolis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ThiefHudFactory extends AbstractHudFactory {

	public ThiefHudFactory(PlayState ps, Canvas c)
	{
		super(ps, c);

		mPlayerIcon = Bitmaps.instance(ps.getContext()).getHudThiefPlayerIcon();
		Bitmap spritebg = Bitmaps.instance(ps.getContext()).getHudThiefSprite();
		
		mSpriteInactive = Bitmap.createBitmap(spritebg, 0, 0, (int) (spritebg.getWidth()*0.5), spritebg.getHeight());
		mSpriteActive = Bitmap.createBitmap(spritebg, (int) (spritebg.getWidth()*0.5), 0, (int) (spritebg.getWidth()*0.5), spritebg.getHeight());
		
	}
	
	public void generateBottomHud()
	{
		//mBottomCanvas.drawBitmap(mBottomHudBackgroundImage, 0, 0, null);
		int cwidth = mBottomHud.getWidth()/3;
		int cheight = mBottomHud.getHeight();
		
		mBottomCanvas.drawBitmap(mSpriteInactive, 0, 0,  null);
		mBottomCanvas.drawBitmap(mSpriteInactive, cwidth, 0,  null);
		mBottomCanvas.drawBitmap(mSpriteInactive, cwidth*2, 0,  null);
		
		ThiefObject thief1 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF1);
		ThiefObject thief2 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF2);
		ThiefObject thief3 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF3);

		
		if(mPs.getThiefTurnState().currentObject == thief1 && thief1.getCurrentDiceValue() != 0){
			mBottomCanvas.drawBitmap(mSpriteActive, 0, 0, null);
		}
		else if(mPs.getThiefTurnState().currentObject == thief2 && thief2.getCurrentDiceValue() != 0){
			mBottomCanvas.drawBitmap(mSpriteActive, cwidth, 0, null);
		}
		else if(mPs.getThiefTurnState().currentObject == thief3 && thief3.getCurrentDiceValue() != 0){
			mBottomCanvas.drawBitmap(mSpriteActive, cwidth*2, 0, null);
		}
		
		float dicey = (cheight - mDiceImage.getHeight()) / 2;
		
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief1.getRolledDiceValue() - 1), (float) (cwidth*0.68), dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief2.getRolledDiceValue() - 1), (float) (cwidth*0.68) + cwidth, dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief3.getRolledDiceValue() - 1), (float) (cwidth*0.68) + 2*cwidth, dicey, null);
		
		mBottomCanvas.drawText(""+thief1.getObjectMoney(), (float) (cwidth*0.45), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+thief2.getObjectMoney(), (float) (cwidth*0.45 + cwidth), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+thief3.getObjectMoney(), (float) (cwidth*0.45 + 2*cwidth), Grid.GRID_SIZE-dicey, paintText);
		
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + cwidth), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + 2*cwidth), 0, null);
		
		mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45), dicey, null);
		mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45 + cwidth), dicey, null);
		mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45 + 2*cwidth), dicey, null);
		
	}
	
	public void generateTopHud() 
	{
		int cwidth = mTopHud.getWidth();
		int cheight = mTopHud.getHeight();
		String totalPoints = "" + 3000;
		mTopCanvas.drawBitmap(Bitmaps.instance(mPs.getContext()).getHudTopImageThieves(), 0, 0, null);
		mTopCanvas.drawText(totalPoints, (float) (cwidth*0.85), (float) (cheight*0.60), paintText);
	}
}