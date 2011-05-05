package android.tjuvochpolis;

import android.graphics.Canvas;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class ThiefObject extends GameObject{

	private int pocketMoney;
	private Rect rectThief;
	private Bitmap thiefIm;
	private long moviestart;
	
	public ThiefObject(String name, GridNode parentNode, int diceValue, int objectMoney, int pocketMoney) {
		super(name, parentNode, diceValue, objectMoney);
		
		this.pocketMoney = pocketMoney;

		this.setDrawXPos(this.getParentNode().getX());
		this.setDrawYPos(this.getParentNode().getY());
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY, Context context) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillfälligt
		//canvas.drawCircle(this.getDrawXPos()+Grid.GRID_SIZE/2 + offsetX, this.getDrawYPos()+Grid.GRID_SIZE/2 + offsetY, Grid.GRID_SIZE/2, paint);
		
		int xPos = (int) this.getDrawXPos() + offsetX ;
		int right = xPos + 48;
		int yPos = (int) this.getDrawYPos() + offsetY-26;
		int bottom = (int) yPos + 74;
		
		
		//lite "kod" som sätter frameraten rätt.
		long now=android.os.SystemClock.uptimeMillis();
		if (moviestart == 0) { // first time
			moviestart = now;
		}
		if(this.isMoving == true){
			
			int direction = Bitmaps.DOWN; //välj denna beroende på vilket håll nästa nod är åt
			
			int relTime = (int)((now - moviestart) % Bitmaps.instance(context).getThiefmovies(direction).duration()) ;
			Bitmaps.instance(context).getThiefmovies(direction).setTime(relTime);
			Bitmaps.instance(context).getThiefmovies(direction).draw(canvas,xPos, yPos, paint);
			//this.invalidate();
		}
		else{
			thiefIm = Bitmaps.instance(context).getThiefImage();
			rectThief = new Rect(xPos, yPos, right, bottom);
			canvas.drawBitmap(thiefIm, null, rectThief, null);
		}
	}

	@Override
	public boolean isWalkable(GridNode node) {
		int type = node.getType();
		if(type == GridNode.STREET || type == GridNode.THIEF_NEST || type == GridNode.BANK) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean canStopHere(GridNode node) {
		// Det står en tjuv på noden
		/*if(node.getGameObject() != null){
			if(node.getGameObject().getClass().equals(ThiefObject.class))
			{
				Log.i("canStopHere","Thief");
				/*if(node.getGameObject().hasMoney()){
					return true;
				}
				else{
					return false;
				}
			}
		}*/
		
		// Det finns en powerup på noden
		/*else if(node.getGameObject().getClass().equals(PowerupObject.class)){
			Log.i("canStopHere","powerup");
			return true;
		}
		// Det står en polis på noden
		else if(node.getGameObject().getClass().equals(CopObject.class))
		{
			Log.i("canStopHere","cop");
			return false;
		}*/
		return false;
	}

	public boolean hasMoney() {
		if(pocketMoney > 0) {
			return true;
		}
		return false;
	}
	
	public void saveState(Context mContext)
	{
		super.saveState(mContext);
		
		SharedPreferences mPrefs = mContext.getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor ed = mPrefs.edit();
	
		ed.putInt(name + "_pocketmoney", pocketMoney);
		
		ed.commit();
	}
}
