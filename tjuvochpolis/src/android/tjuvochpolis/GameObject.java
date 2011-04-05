package android.tjuvochpolis;

import android.graphics.Canvas;

public abstract class GameObject {

	
	GridNode parentNode;
	int pixels = 48; //måste bytas till nåt som vi kommer överens om
	
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
	
	public void checkWalkableNodes(Grid thegrid, int diceValue)
	{
		// Scan area depending on the diceValue
		float startx = this.parentNode.getNodeX();
		float starty = this.parentNode.getNodeY();
		
		// x + diceValue-1
		// y + 
		
		for(int i = 1; i <= diceValue; i++)
		{
			for(int j = 1; j <= diceValue; j++)
			{
				if((i+j) <= diceValue)
				{
					//Could be walkable
					//GridNode newNode = thegrid.gridArray[column][row]
					//this.isWalkable(this.parentNode.getType());
					
				}
			}
		}
	}
	
	
	
	
}
	

