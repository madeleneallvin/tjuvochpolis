package android.tjuvochpolis;

import android.graphics.Canvas;

public class PowerupObject extends GameObject{

	public PowerupObject(GridNode parentNode) {
		super(parentNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWalkable(GridNode node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMoney() {
		// TODO Auto-generated method stub
		return false;
	}

}