package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public abstract class GameObject {

	
	private GridNode mParentNode;
	private int mCurrentDiceValue;
	private int pocketMoney;
	private ArrayList<GridNode> path = new ArrayList<GridNode>();
	private ArrayList<ArrayList<GridNode>> mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
	
	private float mDrawXPos;
	private float mDrawYPos;
	protected int moveToColCoordinate;
	protected int moveToRowCoordinate;
	protected boolean isMoving = true;
	
	public GameObject(GridNode parentNode) {
		this.setParentNode(parentNode);
		
		this.getParentNode().setGameObject(this);
	}
	
	public abstract boolean isWalkable(GridNode node);
	public abstract void doDraw(Canvas canvas, int offsetX, int offsetY); 
	public abstract boolean hasMoney();

	public boolean canStopHere(GridNode node){
		return false;
	}
	
	public void moveTo(GridNode newParent){
		this.getParentNode().setGameObject(null);
		this.setParentNode(newParent);
		
		if(this.getParentNode().getType() == 0) {
			this.getParentNode().setGameObject(this);
		}
		else if(this.getParentNode().getType() == 1) {
			//inte ok att flytta hit		
		}
			
	}
	
	public void doNodeWalker(GridNode currentNode, GridNode previousNode, int diceValue)
	{
		mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
		path = new ArrayList<GridNode>();
		nodeWalker( currentNode,  previousNode,  diceValue);
		
	}
	
	
	private void nodeWalker(GridNode currentNode, GridNode previousNode, int diceValue) {
		
		// Sparar noden man står på i nuvarande path
		path.add(currentNode);
		
		// Tar fram alla möjliga noder man kan gå till från currentNode
		ArrayList<GridNode> nextNodes = this.getNextNodes(currentNode, previousNode);
		
		
		// Kolla gameobjects
	/*	if(nextNodes.size() != 0 && this.canStopHere(currentNode))
		{
			// Save the node
			Log.i("canStopHere",currentNode.getGameObject().getClass().toString());
		}*/
		/*
		// Kolla nodetypes, spara undan om det är ett tillåtet hus
		if(this.isWalkable(currentNode))
		{
			// Save the node
		}
		*/
		//Om tärningen visar 0, lägg till aktuella noden, och hoppa ur.
		if(diceValue == 0) {
			// Sparar undan en möjlig väg
			mPossiblePaths.add(path);
			//Skriver ut alla paths
		//	for(int i = 0; i < path.size(); i++)
			//{
				//Log.i("Path", i + ": col=" + path.get(i).getNodeX() + " row=" + path.get(i).getNodeY());
		//	}
			// Rensar vägen
			path.remove(path.size()-1);
			return;
		}
		//Om det inte finns några nextNodes så står man i en återvändsgränd
		if(nextNodes.size() == 0) {
			path.remove(path.size()-1);
			return;
		}
		
		// loopar alla möjliga vägar
		for(GridNode nextNode : nextNodes) {
			// Anropar nodeWalker igen med ett mindre tärningsvärde och nästa nod
			this.nodeWalker(nextNode, currentNode, diceValue-1);
		}
		
		path.remove(path.size()-1);
		return;
	}
	
	
	private ArrayList<GridNode> getNextNodes(GridNode currentNode, GridNode previousNode){
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
	
	//vad hände här :O
	
	public void moveToCoordinates(int rowCoordinate, int colCoordinate)
	{
		this.moveToRowCoordinate = rowCoordinate;
		this.moveToColCoordinate = colCoordinate;
		
		isMoving = true;
	}
	
	public void drawHighlightSquare(Canvas canvas, int OffsetX, int OffsetY){
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		//Log.i("Y U NO WORK HERE???",  "" +mPossiblePaths.size() );
			for(int i = 0; i < mPossiblePaths.size(); i++)
			{//Log.i("test1", "Y U NO WORK???" + mPossiblePaths.size());
				int tempjSize = mPossiblePaths.get(i).size();
				//Log.i("Y U NO WORK HERE???",  "" +tempjSize );
				for(int j=0; j<tempjSize; j++){
					int xPos = getPossiblePaths().get(i).get(j).getPixelX();
					int yPos = getPossiblePaths().get(i).get(j).getPixelY();
					//Log.i("test1", "Y U NO WORK HERE EITHER???"+ getPossiblePaths().get(i).get(j).getNodeX());
					//Log.i("POSITIONS", ""+ (getPossiblePaths().get(i).get(j).getNodeX())+ " " + + (getPossiblePaths().get(i).get(j).getNodeY()));
					
					//canvas.drawCircle( xPos, yPos, 10, paint);
				canvas.drawCircle(xPos-OffsetX+Grid.GRID_SIZE/2, yPos-OffsetY+Grid.GRID_SIZE/2, Grid.GRID_SIZE/2, paint);
					
				}
			}
		
		}
	
	
	public int getCurrentDiceValue()
	{
		return mCurrentDiceValue;
	}
	
	public void setCurrentDiceValue(int val)
	{
		mCurrentDiceValue = val;
	}

	public void setDrawXPos(float mDrawXPos) {
		this.mDrawXPos = mDrawXPos;
	}

	public float getDrawXPos() {
		return mDrawXPos;
	}

	public void setDrawYPos(float mDrawYPos) {
		this.mDrawYPos = mDrawYPos;
	}

	public float getDrawYPos() {
		return mDrawYPos;
	}

	public void setParentNode(GridNode mParentNode) {
		this.mParentNode = mParentNode;
	}

	public GridNode getParentNode() {
		return mParentNode;
	}

	public void setPossiblePaths(ArrayList<ArrayList<GridNode>> mPossiblePaths) {
		this.mPossiblePaths = mPossiblePaths;
	}

	public ArrayList<ArrayList<GridNode>> getPossiblePaths() {
		return mPossiblePaths;
	}
}
	

