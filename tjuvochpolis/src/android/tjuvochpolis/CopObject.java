package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CopObject extends GameObject{
	
	public int currentDiceValue;
	
	public CopObject(GridNode parentNode) {
		super(parentNode);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isWalkable(GridNode node){
		int type = node.getType();
		if(type == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.BLUE); //bara tillfälligt
		//canvas.drawCircle(parentNode.getNodeX()*pixels+pixels/2, parentNode.getNodeY()*pixels+pixels/2, pixels/2, paint);
		//canvas.drawCircle(this.mDrawXPos+pixels/2, this.mDrawYPos+pixels/2 , pixels/2, paint);
		canvas.drawCircle(this.mDrawXPos+Grid.GRID_SIZE/2, this.mDrawYPos+Grid.GRID_SIZE/2 , Grid.GRID_SIZE/2, paint);
		
				//parentNode.getX()*pixels,  parentNode.getY()*pixels, parentNode.getX()*pixels+pixels, parentNode.getY()*pixels+pixels, paint); // måste sätta in getX, getY * # pixlar
		
	}

}
