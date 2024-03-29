package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class CopObject extends GameObject{

	private Rect rectCop;
	private Bitmap copIm;
	private long moviestart;
	private int direction;
	private ThiefObject thiefCaught = null;
	
	public CopObject(String name,GridNode parentNode, int diceValue, int rolledDiceValue, int objectMoney, int waitingleft) {
		super(name, parentNode, diceValue, rolledDiceValue, objectMoney, waitingleft);
		
		this.isMoving = false;
		this.setDrawXPos(this.getParentNode().getX());
		this.setDrawYPos(this.getParentNode().getY());
	}
	
	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY, Context context) {

		Paint paint = new Paint();
		paint.setColor(Color.BLUE); 
		
		int xPos = (int) this.getDrawXPos() + offsetX ;
		int right = xPos + 48;
		int yPos = (int) this.getDrawYPos() + offsetY-24;
		int bottom = (int) yPos + 72;
		
		//lite "kod" som s�tter frameraten r�tt.
		long now=android.os.SystemClock.uptimeMillis();
		if (moviestart == 0) { // first time
			moviestart = now;
		}
		
		//ta reda p� vilket h�ll polisen ska v�nda sig n�r han g�r
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
			
			int relTime = (int)((now - moviestart) % Bitmaps.instance(context).getCopmovies(direction).duration()) ;
			Bitmaps.instance(context).getCopmovies(direction).setTime(relTime);
			Bitmaps.instance(context).getCopmovies(direction).draw(canvas,xPos, yPos, paint);
			//this.invalidate();
		}
		else{
				//gr�
				if(this.getCurrentDiceValue() ==0 ){
				
				Bitmap copIm = Bitmaps.instance(context).getCopGray();
				rectCop = new Rect(xPos, yPos, right,bottom);
				canvas.drawBitmap(copIm,null,rectCop, null);
				direction = Bitmaps.DOWN;
				}
				
			  //markerad
				else if(this.isActive){
							
					Bitmap copIm  = Bitmaps.instance(context).getCopMarked();
				rectCop = new Rect(xPos, yPos, right,bottom);
				canvas.drawBitmap(copIm,null,rectCop, null);
				direction = Bitmaps.DOWN;
				}		
				
				//vanlig
				else if (this.isActive == false){
				Bitmap copIm = Bitmaps.instance(context).getCopImage();
				rectCop = new Rect(xPos, yPos, right, bottom);
				canvas.drawBitmap(copIm, null, rectCop, null);
				direction = Bitmaps.DOWN;
				}
			}
		}

	
	public boolean isWalkable(GridNode node){
		int type = node.getType();

		if(type == GridNode.STREET || type == GridNode.POLICE_STATION)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isNodeOccupied(GridNode node) {
		if(node.getGameObject() == null) {
			return false;
		}
		else {
			if(node.getGameObject().getClass() == CopObject.class || !node.getGameObject().hasMoney()){
				return true;
			}
			else{
				return false;
			}
		}
	}

	public boolean canStopHere(GridNode node)
	{
		if(node.getType() == GridNode.POLICE_STATION){
			return true;
		}
		
		// Det st�r en tjuv p� noden
		if(node.getGameObject() != null){
			if(node.getGameObject().getClass().equals(ThiefObject.class))
			{
				if(node.getGameObject().hasMoney()){
					return true;
				}
				else{
					return false;
				}
			}
			// Det finns en powerup p� noden
			else if(node.getGameObject().getClass().equals(PowerupObject.class)){

				return true;
			}
			// Det st�r en polis p� noden
			else if(node.getGameObject().getClass().equals(CopObject.class))
			{

				return false;
			}
			return false;
		}
		return false;

	}

	@Override
	public boolean hasMoney() {
		return false;
	}
	
	public void setThiefCaught(ThiefObject thiefCaught) {
		this.thiefCaught = thiefCaught;
	}

	public ThiefObject getThiefCaught() {
		return thiefCaught;
	}
}
