package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ThiefObject extends GameObject{

	public ThiefObject(GridNode parentNode) {
		super(parentNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED); //bara tillfälligt
		canvas.drawCircle(parentNode.getX()*pixels+pixels/2, parentNode.getY()*pixels+pixels/2, pixels/2, paint);
		// TODO Auto-generated method stub
		
	}

}
