package android.tjuvochpolis;

import android.graphics.Canvas;

public abstract class GameObject {

	
	GridNode parentNode;
	int pixels = 30; //m�ste bytas till n�t som vi kommer �verens om

	
	
	public GameObject(GridNode parentNode) {
		this.parentNode = parentNode;
	}
	
	public abstract void doDraw(Canvas canvas); 
	
	public void moveTo(GridNode newParent){
		parentNode = newParent;
		
		
	}
}
	

