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
	
	public CopObject(String name,GridNode parentNode) {
		super(name, parentNode);

		this.setDrawXPos(this.getParentNode().getX());
		this.setDrawYPos(this.getParentNode().getY());
	}
	
	public boolean isWalkable(GridNode node){
		int type = node.getType();

		if(type == GridNode.STREET || type == GridNode.POLICE_STATION || type == GridNode.TELEGRAPH)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean canStopHere(GridNode node)
	{
		// Det står en tjuv på noden
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
			// Det finns en powerup på noden
			else if(node.getGameObject().getClass().equals(PowerupObject.class)){

				return true;
			}
			// Det står en polis på noden
			else if(node.getGameObject().getClass().equals(CopObject.class))
			{

				return false;
			}
			return false;
		}
		return false;

	}
	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY, Context context) {

		Paint paint = new Paint();
		paint.setColor(Color.BLUE); 
		
		int left = (int) this.getDrawXPos() + offsetX + 7;
		int right = left + 33;
		int top = (int) this.getDrawYPos() + offsetY;
		int bottom = (int) top + 48;
		
		copIm = Bitmaps.instance(context).getCopImage();
		rectCop = new Rect(left, top, right, bottom);
		canvas.drawBitmap(copIm, null, rectCop, null);
	}

	@Override
	public boolean hasMoney() {
		return false;
	}
}
