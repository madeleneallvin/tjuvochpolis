package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ThiefHudFactory extends AbstractHudFactory {

	public ThiefHudFactory(PlayState ps, Canvas c)
	{
		super(ps, c);
		mPlayerIcon = Bitmaps.instance(ps.getContext()).getHudThiefPlayerIcon();
	}
	
	public void generateBottomHud()
	{
		//mBottomCanvas.drawBitmap(mBottomHudBackgroundImage, 0, 0, null);
		mBottomCanvas.drawARGB(255, 130, 130, 130);
		int cwidth = mBottomHud.getWidth()/3;
		int cheight = mBottomHud.getHeight();
		
		ThiefObject thief1 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF1);
		ThiefObject thief2 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF2);
		ThiefObject thief3 = (ThiefObject) mPs.getGameObject(PlayState.mObjectIndex.THIEF3);
		
		Paint paint = new Paint();
		paint.setARGB(255, 190, 0, 0);
		
		if(mPs.getThiefTurnState().currentObject == thief1 && thief1.getCurrentDiceValue() != 0){
			mBottomCanvas.drawRect(new Rect(0, 0, cwidth, cheight), paint);
		}
		else if(mPs.getThiefTurnState().currentObject == thief2 && thief2.getCurrentDiceValue() != 0){
			mBottomCanvas.drawRect(new Rect(cwidth, 0, cwidth * 2, cheight), paint);
		}
		else if(mPs.getThiefTurnState().currentObject == thief3 && thief3.getCurrentDiceValue() != 0){
			mBottomCanvas.drawRect(new Rect(2*cwidth, 0, cwidth * 3, cheight), paint);
		}
		
		float dicey = (cheight - mDiceImage.getHeight()) / 2;
		
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief1.getRolledDiceValue() - 1), (float) (cwidth*0.68), dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief2.getRolledDiceValue() - 1), (float) (cwidth*0.68) + cwidth, dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(thief3.getRolledDiceValue() - 1), (float) (cwidth*0.68) + 2*cwidth, dicey, null);
		
		Paint paintText = new Paint();
		paintText.setARGB(255, 255, 255, 255);
		
		mBottomCanvas.drawText(""+thief1.getObjectMoney(), (float) (cwidth*0.35), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+thief2.getObjectMoney(), (float) (cwidth*0.35 + cwidth), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+thief3.getObjectMoney(), (float) (cwidth*0.35 + 2*cwidth), Grid.GRID_SIZE-dicey, paintText);
		
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + cwidth), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + 2*cwidth), 0, null);
	}
}