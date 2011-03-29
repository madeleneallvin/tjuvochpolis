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
		canvas.drawRect(parentNode.getX()*pixels,parentNode.getY()*pixels , 1*pixels, 1*pixels, paint); // måste sätta in getX, getY * # pixlar
		
		
	}

}
