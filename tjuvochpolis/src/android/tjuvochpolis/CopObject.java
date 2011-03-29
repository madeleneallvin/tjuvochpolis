package android.tjuvochpolis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CopObject extends GameObject{

	public CopObject(GridNode parentNode) {
		super(parentNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.BLUE); //bara tillfälligt
		canvas.drawCircle(parentNode.getX()*pixels+pixels/2, parentNode.getY()*pixels+pixels/2, pixels/2, paint);
				//parentNode.getX()*pixels,  parentNode.getY()*pixels, parentNode.getX()*pixels+pixels, parentNode.getY()*pixels+pixels, paint); // måste sätta in getX, getY * # pixlar
		
		
	}

}
