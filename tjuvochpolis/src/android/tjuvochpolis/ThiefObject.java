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
	public void doDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillfälligt
		canvas.drawCircle(mParentNode.getNodeX()*pixels+pixels/2, mParentNode.getNodeY()*pixels+pixels/2, pixels/2, paint);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWalkable(GridNode node) {
		// TODO Auto-generated method stub
		return false;
	}

}
