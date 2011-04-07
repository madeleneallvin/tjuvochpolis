package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ThiefObject extends GameObject{

	private int pixels = Grid.GRID_SIZE;
	
	public ThiefObject(GridNode parentNode) {
		super(parentNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillfälligt
		canvas.drawCircle(this.getDrawXPos()+Grid.GRID_SIZE/2 + offsetX, this.getDrawYPos()+Grid.GRID_SIZE/2 + offsetY, Grid.GRID_SIZE/2, paint);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWalkable(GridNode node) {
		// TODO Auto-generated method stub
		return false;
	}

}
