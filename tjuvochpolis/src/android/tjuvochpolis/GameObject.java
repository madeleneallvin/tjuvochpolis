package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

public abstract class GameObject {

	
	GridNode parentNode;
	int currentDiceValue;
	ArrayList<GridNode> possibleNextNodes = new ArrayList<GridNode>();
	int pixels = 48; //måste bytas till nåt som vi kommer överens om

	float mDrawXPos;
	float mDrawYPos;
	protected int moveToColCoordinate;
	protected int moveToRowCoordinate;
	protected boolean isMoving = true;
	
	public GameObject(GridNode parentNode) {
		this.parentNode = parentNode;
		
		this.parentNode.setGameObject(this);
	}
	
	public abstract boolean isWalkable(GridNode node);
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
	
	
	
	public void nodeWalker(GridNode currentNode, GridNode previousNode, int diceValue) {
		ArrayList<GridNode> nextNodes = this.getNextNodes(currentNode, previousNode);
		
		Log.i("Dice value", "Dice value = " + diceValue);
		
		//Om tärningen visar 0, lägg till aktuella noden, och hoppa ur.
		if(diceValue == 0) {
			possibleNextNodes.add(currentNode);
			return;
		}
		if(nextNodes.size() == 0) {
			return;
		}
		
		//for each
		for(GridNode nextNode : nextNodes) {
			Log.i("Next node", "col=" + nextNode.getNodeX() + " row=" + nextNode.getNodeY());
			this.nodeWalker(nextNode, currentNode, diceValue-1);
			
		}
		
		
	}
	
	
	public ArrayList<GridNode> getNextNodes(GridNode currentNode, GridNode previousNode){
		// hämta alla noder runt current
		// ta inte med previous!
		ArrayList<GridNode> possibleNextNodes = new ArrayList<GridNode>();

		//kollar om övre nod inte är samma som föregående nod och att den går att gå på 
		if(currentNode.getUpNode() != null && !currentNode.getUpNode().equals(previousNode) && this.isWalkable(currentNode.getUpNode())) {
			possibleNextNodes.add(currentNode.getUpNode());
		}
		
		//kollar om nedre nod inte är samma som föregående nod och att den går att gå på
		if(currentNode.getDownNode() != null && !currentNode.getDownNode().equals(previousNode) && this.isWalkable(currentNode.getDownNode())) {
			possibleNextNodes.add(currentNode.getDownNode());
		}
		
		//kollar om vänstra nod inte är samma som föregående nod och att den går att gå på
		if(currentNode.getLeftNode() != null && !currentNode.getLeftNode().equals(previousNode) && this.isWalkable(currentNode.getLeftNode())) {
			possibleNextNodes.add(currentNode.getLeftNode());
		}
		
		//kollar om högra nod inte är samma som föregående nod och att den går att gå på
		if(currentNode.getRightNode() != null && !currentNode.getRightNode().equals(previousNode) && this.isWalkable(currentNode.getRightNode())) {
			possibleNextNodes.add(currentNode.getRightNode());
		}
		
		
		return possibleNextNodes;
	}
	
	
	/*public ArrayList<int[]> checkWalkableNodes(Grid thegrid)
	{
		ArrayList<int[]> walkableGridNodes = new ArrayList<int[]>();
		int[] temp = new int[2];
		
		boolean walkable = false;
		
		// Scan area depending on the diceValue
		int startCol = this.parentNode.getNodeX();
		int startRow = this.parentNode.getNodeY();

		//if(this.getClass() == CopObject.class);
		
		Log.i("test1", "Dice value="+this.diceValue);
		Log.i("test1", "startRow=" + startRow + " startCol=" + startCol);
		
		// x + diceValue-1
		// y + 
		
//		if(this.getClass() == CopObject.class) {
//			Log.i("test1", this.toString());
//		}
		
		for(int col = -this.diceValue; col <= this.diceValue; col++) 
		{
			for(int row = -this.diceValue; row <= this.diceValue; row++) 
			{
				if((Math.abs(col)+Math.abs(row)) <= this.diceValue)
				{
					//Could be walkable
					//GridNode newNode = thegrid.gridArray[column][row]
					//this.isWalkable(this.parentNode.getType());
					Log.i("test1", "row=" + row + " col=" + col);
					if(this.isWalkable(thegrid.gridArray[startRow + row][startCol + col])){
						
						//possible to walk to??
						
						temp[0] = row;
						temp[1] = col;
						walkableGridNodes.add(temp);
					}
				}
			}
		}
		
		
		
		return walkableGridNodes;
	}
	*/
	
	
	
	public void moveToCoordinates(int rowCoordinate, int colCoordinate)
	{
		this.moveToRowCoordinate = rowCoordinate;
		this.moveToColCoordinate = colCoordinate;
		
		isMoving = true;
	}			
}
	

