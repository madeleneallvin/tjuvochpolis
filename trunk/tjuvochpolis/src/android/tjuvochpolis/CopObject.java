package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class CopObject extends GameObject{

	
	public CopObject(String name,GridNode parentNode) {
		super(name, parentNode);
		
		// TODO Auto-generated constructor stub
		
		this.setDrawXPos(this.getParentNode().getPixelX());
		this.setDrawYPos(this.getParentNode().getPixelY());
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
				Log.i("canStopHere","powerup");
				return true;
			}
			// Det står en polis på noden
			else if(node.getGameObject().getClass().equals(CopObject.class))
			{
				Log.i("canStopHere","cop");
				return false;
			}
			return false;
		}
		return false;
		
	}
	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.BLUE); 
		canvas.drawCircle(this.getDrawXPos()+Grid.GRID_SIZE/2 + offsetX, this.getDrawYPos()+Grid.GRID_SIZE/2 + offsetY, Grid.GRID_SIZE/2, paint);
		
	}
	

	@Override
	public boolean hasMoney() {
		return false;
	}
	


}
