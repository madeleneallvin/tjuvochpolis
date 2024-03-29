package android.tjuvochpolis;

import java.util.Currency;

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

	private Rect rectThief,rectMarkedThief,rectGreyThief;
	private Bitmap thiefIm;
	private long moviestart;
	private int direction;
	private boolean isCaught = false;
	
	public ThiefObject(String name, GridNode parentNode, int diceValue, int rolledDiceValue, int objectMoney, int waitingleft) {
		super(name, parentNode, diceValue, rolledDiceValue, objectMoney, waitingleft);
		
		this.objectMoney = objectMoney;
		this.isMoving = false;
		this.setDrawXPos(this.getParentNode().getX());
		this.setDrawYPos(this.getParentNode().getY());
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY, Context context) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillf�lligt
		//canvas.drawCircle(this.getDrawXPos()+Grid.GRID_SIZE/2 + offsetX, this.getDrawYPos()+Grid.GRID_SIZE/2 + offsetY, Grid.GRID_SIZE/2, paint);
		
		int xPos = (int) this.getDrawXPos() + offsetX ;
		int right = xPos + 48;
		int yPos = (int) this.getDrawYPos() + offsetY-24;
		int bottom = (int) yPos + 72;
		
		//lite "kod" som s�tter frameraten r�tt.
		long now=android.os.SystemClock.uptimeMillis();
		if (moviestart == 0) { // first time
			moviestart = now;
		}
		
		//ta reda p� vilket h�ll tjuven ska v�nda sig n�r han g�r
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
			
			if(this.hasMoney()){
				int relTime = (int)((now - moviestart) % Bitmaps.instance(context).getThiefMoneyMovies(direction).duration()) ;
				Bitmaps.instance(context).getThiefMoneyMovies(direction).setTime(relTime);
				Bitmaps.instance(context).getThiefMoneyMovies(direction).draw(canvas,xPos, yPos, paint);
				
			}else{
				int relTime = (int)((now - moviestart) % Bitmaps.instance(context).getThiefmovies(direction).duration()) ;
				Bitmaps.instance(context).getThiefmovies(direction).setTime(relTime);
				Bitmaps.instance(context).getThiefmovies(direction).draw(canvas,xPos, yPos, paint);
			}
			
			//this.invalidate();
		}
		else if(this.hasMoney()){
			
			if(this.getCurrentDiceValue() == 0 ){
				
			Bitmap thiefGrey = Bitmaps.instance(context).getThiefMoneyGrey();
			rectGreyThief = new Rect(xPos, yPos, right,bottom);
			canvas.drawBitmap(thiefGrey,null,rectGreyThief, null);
			direction = Bitmaps.DOWN;
			}
								
			else if(this.isActive){
				
			Bitmap thiefGold = Bitmaps.instance(context).getThiefMoneyMarked();
			rectMarkedThief = new Rect(xPos, yPos, right,bottom);
			canvas.drawBitmap(thiefGold,null,rectMarkedThief, null);
			direction = Bitmaps.DOWN;
			}
			
			
			//har pengar
			else if (this.isActive == false){
			thiefIm = Bitmaps.instance(context).getThiefMoney();
			rectThief = new Rect(xPos, yPos, right, bottom);
			canvas.drawBitmap(thiefIm, null, rectThief, null);
			direction = Bitmaps.DOWN;
			}
			
					}
		
		else {
			
			if(this.getCurrentDiceValue() ==0 ){
				
			Bitmap thiefGrey = Bitmaps.instance(context).getThiefgray();
			rectGreyThief = new Rect(xPos, yPos, right,bottom);
			canvas.drawBitmap(thiefGrey,null,rectGreyThief, null);
			direction = Bitmaps.DOWN;
			}
			
		  
			else if(this.isActive){
						
			Bitmap thiefGold = Bitmaps.instance(context).getThiefMarked();
			rectMarkedThief = new Rect(xPos, yPos, right,bottom);
			canvas.drawBitmap(thiefGold,null,rectMarkedThief, null);
			direction = Bitmaps.DOWN;
			}
				
			
			else if (this.isActive == false){
			thiefIm = Bitmaps.instance(context).getThiefImage();
			rectThief = new Rect(xPos, yPos, right, bottom);
			canvas.drawBitmap(thiefIm, null, rectThief, null);
			direction = Bitmaps.DOWN;
			}
			
			
			
			
		}
	}

	@Override
	public boolean isWalkable(GridNode node) {
		int type = node.getType();
		
		if(node.getGameObject() != null){
			if(this.hasMoney() && node.getGameObject().getClass().equals(CopObject.class)){
				Log.i("polis", this.name+" har pengar");
				return false;
			}
		}
		
		if(type == GridNode.STREET || type == GridNode.THIEF_NEST || type == GridNode.BANK) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isNodeOccupied(GridNode node) {
		if(node.getGameObject() == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean canStopHere(GridNode node) {
		if(node.getType() == GridNode.THIEF_NEST || node.getType() == GridNode.BANK){
			return true;
		}else{
			return false;
		}
	}

	public boolean hasMoney() {
		if(objectMoney > 0) {
			return true;
		}
		return false;
	}

	public int getMoney(){
		return this.objectMoney;
	}

	public void saveState(Context mContext)
	{
		super.saveState(mContext);
		
		SharedPreferences mPrefs = mContext.getSharedPreferences("gamePrefs", Context.MODE_WORLD_READABLE);
		
		SharedPreferences.Editor ed = mPrefs.edit();
	
		ed.putInt(name + "_objectMoney", objectMoney);
		
		ed.commit();
	}

	public void setCaught(boolean isCaught) {
		this.isCaught = isCaught;
	}

	public boolean isCaught() {
		return isCaught;
	}
}
