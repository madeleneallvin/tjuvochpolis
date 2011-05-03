package android.tjuvochpolis;

import java.util.Random;

public class BankObject extends GameStaticObject {

	public BankObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(getBankMoney());
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
