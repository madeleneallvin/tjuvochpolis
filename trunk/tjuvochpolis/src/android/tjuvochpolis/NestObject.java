package android.tjuvochpolis;

import android.graphics.Canvas;

public class NestObject extends GameObject {

	public NestObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(0);
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {

	}

	@Override
	public boolean hasMoney() {
		if(this.getObjectMoney() == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean isWalkable(GridNode node) {
		return false;
	}
	
	public void storeMoney(int money){
		this.setObjectMoney(this.getObjectMoney() + money);
	}
	
	public int takeMoney(){
		return this.getObjectMoney();
	}
	
}
