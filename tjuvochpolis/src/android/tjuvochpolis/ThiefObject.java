package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ThiefObject extends GameObject{

	private int pocketMoney;
	
	public ThiefObject(String name, GridNode parentNode) {
		super(name, parentNode);

		this.setDrawXPos(this.getParentNode().getPixelX());
		this.setDrawYPos(this.getParentNode().getPixelY());
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillfälligt
		canvas.drawCircle(this.getDrawXPos()+Grid.GRID_SIZE/2 + offsetX, this.getDrawYPos()+Grid.GRID_SIZE/2 + offsetY, Grid.GRID_SIZE/2, paint);
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
		//Log.i("canStopHere","fail or not?");
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
}
