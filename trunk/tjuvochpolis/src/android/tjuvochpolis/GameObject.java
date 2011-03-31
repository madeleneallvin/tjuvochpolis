package android.tjuvochpolis;

import android.graphics.Canvas;

public abstract class GameObject {

	
	GridNode parentNode;
	int pixels = 48; //m�ste bytas till n�t som vi kommer �verens om

	
	
	public GameObject(GridNode parentNode) {
		this.parentNode = parentNode;
		
		this.parentNode.setGameObject(this);
	}
	
	public abstract void doDraw(Canvas canvas); 
	
	public void moveTo(GridNode newParent){
		this.parentNode.setGameObject(null);
		this.parentNode = newParent;
		if(this.parentNode.getType() == 0) {
			this.parentNode.setGameObject(this);
		}
		else if(this.parentNode.getType() == 1) {
			//inte ok att flytta hit		
		}
		
		
	}
}
	

