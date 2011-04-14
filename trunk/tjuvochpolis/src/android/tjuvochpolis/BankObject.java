package android.tjuvochpolis;

import java.util.Random;

import android.graphics.Canvas;
import android.util.Log;

public class BankObject extends GameObject {

	public BankObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(getBankMoney());
		
	//	Log.i("BankObject", "" + this.getObjectMoney());
	}

	@Override
	public void doDraw(Canvas canvas, int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasMoney() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWalkable(GridNode node) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getBankMoney(){
		
		Random r = new Random();
		
		
		float rand = r.nextFloat()+1;
		int mapCenterHeight = (int)Grid.getHeight()/2;
		int mapCenterWidth = (int)Grid.getWidth()/2;
		
		int x = this.getParentNode().getNodeX();
		int y = this.getParentNode().getNodeY();
		
		float dist = Math.abs(mapCenterHeight - x) + Math.abs(mapCenterWidth - y);
		
		float baseMoney = 10000;
		
		int temp = (int)(rand * baseMoney/dist);
		
		return temp;
		
	}
}
