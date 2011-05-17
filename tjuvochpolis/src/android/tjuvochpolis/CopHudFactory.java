package android.tjuvochpolis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CopHudFactory extends AbstractHudFactory {
	
	public CopHudFactory(PlayState ps, Canvas c)
	{
		super(ps, c);

		mPlayerIcon = Bitmaps.instance(ps.getContext()).getHudCopPlayerIcon();
		
		Bitmap spritebg = Bitmaps.instance(ps.getContext()).getHudCopSprite();
		
		mSpriteInactive = Bitmap.createBitmap(spritebg, 0, 0, (int) (spritebg.getWidth()*0.5), spritebg.getHeight());
		mSpriteActive = Bitmap.createBitmap(spritebg, (int) (spritebg.getWidth()*0.5), 0, (int) (spritebg.getWidth()*0.5), spritebg.getHeight());
		

		//mCopBgInactive = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.copInactive);
		//mCopBgActive = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.copActive);
	}
	
	public void generateBottomHud()
	{
		
		int cwidth = mBottomHud.getWidth()/3;
		int cheight = mBottomHud.getHeight();

		CopObject cop1 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP1);
		CopObject cop2 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP2);
		CopObject cop3 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP3);
		
		if(mPs.getCopTurnState().currentObject == cop1 && cop1.getCurrentDiceValue() != 0){
		//if(cop1.isActive && cop1.getCurrentDiceValue() != 0) {
			mBottomCanvas.drawBitmap(mSpriteActive, 0, 0, null);
		}
		else {
			mBottomCanvas.drawBitmap(mSpriteInactive, 0, 0,  null);
		}
		
		if(mPs.getCopTurnState().currentObject == cop2 && cop2.getCurrentDiceValue() != 0){
			mBottomCanvas.drawBitmap(mSpriteActive, cwidth, 0, null);
		}
		else {
			mBottomCanvas.drawBitmap(mSpriteInactive, cwidth, 0,  null);
		}
		
		if(mPs.getCopTurnState().currentObject == cop3 && cop3.getCurrentDiceValue() != 0){
			mBottomCanvas.drawBitmap(mSpriteActive, cwidth*2, 0, null);
		}
		else {
			mBottomCanvas.drawBitmap(mSpriteInactive, cwidth*2, 0,  null);
		}
		
		float dicey = (cheight - mDiceImage.getHeight()) / 2;
		
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop1.getRolledDiceValue() - 1), (float) (cwidth*0.68), dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop2.getRolledDiceValue() - 1), (float) (cwidth*0.68) + cwidth, dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop3.getRolledDiceValue() - 1), (float) (cwidth*0.68) + 2*cwidth, dicey, null);

		//mBottomCanvas.drawText(""+cop1.getObjectMoney(), (float) (cwidth*0.45), Grid.GRID_SIZE-dicey, paintText);
		//mBottomCanvas.drawText(""+cop2.getObjectMoney(), (float) (cwidth*0.45 + cwidth), Grid.GRID_SIZE-dicey, paintText);
		//mBottomCanvas.drawText(""+cop3.getObjectMoney(), (float) (cwidth*0.45 + 2*cwidth), Grid.GRID_SIZE-dicey, paintText);
		
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.15), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.15 + cwidth), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.15 + 2*cwidth), 0, null);
		
		//mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45), dicey, null);
		//mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45 + cwidth), dicey, null);
		//mBottomCanvas.drawBitmap(mCoin, (float) (cwidth*0.45 + 2*cwidth), dicey, null);
		
		if(cop1.getCurrentDiceValue() == 0 || cop1.getWaitingLeft() != 0) {
			mBottomCanvas.drawRect(new Rect(0, 0, cwidth,cheight), shadePaint);
		}
		if(cop2.getCurrentDiceValue() == 0 || cop2.getWaitingLeft() != 0) {
			mBottomCanvas.drawRect(new Rect(cwidth, 0, 2*cwidth,cheight), shadePaint);
		}
		if(cop3.getCurrentDiceValue() == 0 || cop3.getWaitingLeft() != 0) {
			mBottomCanvas.drawRect(new Rect(2*cwidth, 0, 3*cwidth,cheight), shadePaint);
		}
		
		if(cop1.isDrawWaitingLeft())
		{
			mBottomCanvas.drawText(""+(cop1.getWaitingLeft() + 1),
									(float) (cwidth/2 - paintWaitingLeft.getTextSize()*0.3), 
									(float) (cheight - cheight*0.2),
									paintWaitingLeft);
		}
		if(cop2.isDrawWaitingLeft())
		{
			mBottomCanvas.drawText(""+(cop2.getWaitingLeft() + 1),
									(float) (cwidth/2 - paintWaitingLeft.getTextSize()*0.3) + cwidth, 
									(float) (cheight - cheight*0.2),
									paintWaitingLeft);
		}	
		if(cop3.isDrawWaitingLeft())
		{
			mBottomCanvas.drawText(""+(cop3.getWaitingLeft() + 1),
									(float) (cwidth/2 - paintWaitingLeft.getTextSize()*0.3) + cwidth*2, 
									(float) (cheight - cheight*0.2),
									paintWaitingLeft);
		}
	}

	public void generateTopHud() 
	{
		int cwidth = mTopHud.getWidth();
		int cheight = mTopHud.getHeight();
		mTopCanvas.drawBitmap(Bitmaps.instance(mPs.getContext()).getHudTopImageCops(), 0, 0, null);
		mTopCanvas.drawText(mPs.calculateCopTeamMoney() + "", (float) (cwidth*0.83), (float) (cheight/2 + paintBigText.getTextSize()/2), paintBigText);
	}
}
