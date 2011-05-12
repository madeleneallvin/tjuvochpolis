package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CopHudFactory extends AbstractHudFactory {
	
	public CopHudFactory(PlayState ps, Canvas c)
	{
		super(ps, c);

		mPlayerIcon = Bitmaps.instance(ps.getContext()).getHudCopPlayerIcon();
		//mCopBgInactive = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.copInactive);
		//mCopBgActive = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.copActive);
	}
	
	public void generateBottomHud()
	{
		//mBottomCanvas.drawBitmap(mBottomHudBackgroundImage, 0, 0, null);
		mBottomCanvas.drawARGB(255, 130, 130, 130);
		int cwidth = mBottomHud.getWidth()/3;
		int cheight = mBottomHud.getHeight();
		
		CopObject cop1 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP1);
		CopObject cop2 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP2);
		CopObject cop3 = (CopObject) mPs.getGameObject(PlayState.mObjectIndex.COP3);
				
		Paint paint = new Paint();
		paint.setARGB(255, 0, 0, 190);
		
		if(mPs.getCopTurnState().currentObject == cop1 && cop1.getCurrentDiceValue() != 0){
		//if(cop1.isActive && cop1.getCurrentDiceValue() != 0) {
			mBottomCanvas.drawRect(new Rect(0, 0, cwidth, cheight), paint);
		}
		else if(mPs.getCopTurnState().currentObject == cop2 && cop2.getCurrentDiceValue() != 0){
			mBottomCanvas.drawRect(new Rect(cwidth, 0, cwidth * 2, cheight), paint);
		}
		else if(mPs.getCopTurnState().currentObject == cop3 && cop3.getCurrentDiceValue() != 0){
			mBottomCanvas.drawRect(new Rect(2*cwidth, 0, cwidth * 3, cheight), paint);
		}
		
		float dicey = (cheight - mDiceImage.getHeight()) / 2;
		
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop1.getRolledDiceValue() - 1), (float) (cwidth*0.68), dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop2.getRolledDiceValue() - 1), (float) (cwidth*0.68) + cwidth, dicey, null);
		mBottomCanvas.drawBitmap(mDiceSegments.get(cop3.getRolledDiceValue() - 1), (float) (cwidth*0.68) + 2*cwidth, dicey, null);

		Paint paintText = new Paint();
		paintText.setARGB(255, 255, 255, 255);
		
		mBottomCanvas.drawText(""+cop1.getObjectMoney(), (float) (cwidth*0.35), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+cop2.getObjectMoney(), (float) (cwidth*0.35 + cwidth), Grid.GRID_SIZE-dicey, paintText);
		mBottomCanvas.drawText(""+cop3.getObjectMoney(), (float) (cwidth*0.35 + 2*cwidth), Grid.GRID_SIZE-dicey, paintText);
		
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + cwidth), 0, null);
		mBottomCanvas.drawBitmap(mPlayerIcon, (float) (cwidth*0.05 + 2*cwidth), 0, null);
	}

}
