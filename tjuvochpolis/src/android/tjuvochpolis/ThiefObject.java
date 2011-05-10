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
	private int direction;
	
	public ThiefObject(String name, GridNode parentNode, int diceValue, int objectMoney, int pocketMoney) {
		super(name, parentNode, diceValue, objectMoney);
		
		this.isMoving = false;
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
		int yPos = (int) this.getDrawYPos() + offsetY-24;
		int bottom = (int) yPos + 72;
		
		//lite "kod" som sätter frameraten rätt.
		long now=android.os.SystemClock.uptimeMillis();
		if (moviestart == 0) { // first time
			moviestart = now;
		}
		
		//ta reda på vilket håll tjuven ska vända sig när han går
		if(this.isMoving == true){
			//Log.i("path size", ""+this.getMovePath().size());
			//Log.i("path position", ""+this.getCurrentPathPosition());
			if(this.getCurrentPathPosition()>0 && this.getCurrentPathPosition() < (this.getMovePath().size())){
			
				
				
				//Log.i("nextNodeX", ""+this.getMovePath().get(this.getCurrentPathPosition()+1).getNodeX());
				if(this.getMovePath().get(this.getCurrentPathPosition()-1).getX() == this.getMovePath().get(this.getCurrentPathPosition()).getX()){
					if(this.getMovePath().get(this.getCurrentPathPosition()-1).getY() < this.getMovePath().get(this.getCurrentPathPosition()).getY()){
						direction = Bitmaps.DOWN;
					}else{
						direction = Bitmaps.UP;
					}
				}
				else if( this.getMovePath().get(this.getCurrentPathPosition()-1).getX() < this.getMovePath().get(this.getCurrentPathPosition()).getX())
				{
					direction = Bitmaps.RIGHT;
				}
				else{
					direction = Bitmaps.LEFT;
				}
			}
			
			int relTime = (int)((now - moviestart) % Bitmaps.instance(context).getThiefmovies(direction).duration()) ;
			Bitmaps.instance(context).getThiefmovies(direction).setTime(relTime);
			Bitmaps.instance(context).getThiefmovies(direction).draw(canvas,xPos, yPos, paint);
			//this.invalidate();
		}
		else{
			Resources res = context.getResources();
			thiefIm = Bitmaps.instance(context).getThiefImage();
			rectThief = new Rect(xPos, yPos, right, bottom);
			canvas.drawBitmap(thiefIm, null, rectThief, null);
			direction = Bitmaps.DOWN;
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
		if(node.getType() == GridNode.THIEF_NEST || node.getType() == GridNode.BANK){
			return true;
		}
		
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
	@Override
	public int getPocketMoney(){
		return pocketMoney;
	
	}
	@Override
	public void setPocketMoney(int money){
		this.pocketMoney = money;
	}
}
