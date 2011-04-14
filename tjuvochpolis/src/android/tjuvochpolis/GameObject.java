package android.tjuvochpolis;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public abstract class GameObject {

	
	private GridNode mParentNode;
	private int mCurrentDiceValue;
	private int objectMoney;
	private int objectIndex;
	private String name;
	private ArrayList<GridNode> path = new ArrayList<GridNode>();
	private ArrayList<ArrayList<GridNode>> mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
	private ArrayList<GridNode> mMovePath = new ArrayList<GridNode>();
	
	private float mDrawXPos;
	private float mDrawYPos;
	protected int moveToColCoordinate;
	protected int moveToRowCoordinate;
	protected boolean isMoving = true;
	protected boolean objectFinishedMoving = false;
	public GameObject(String name,GridNode parentNode) {
		this.setParentNode(parentNode);
		this.name = name;
		
		this.getParentNode().setGameObject(this);
	}
	
	public abstract boolean isWalkable(GridNode node);
	public abstract void doDraw(Canvas canvas, int offsetX, int offsetY); 
	public abstract boolean hasMoney();

	public boolean canStopHere(GridNode node){
		return false;
	}
	
	/*public void moveTo(GridNode newParent){
		this.getParentNode().setGameObject(null);
		this.setParentNode(newParent);
		Log.i("funktion", "moveto");
		if(this.getParentNode().getType() == GridNode.STREET) {
			this.getParentNode().setGameObject(this);
		}
		else if(this.getParentNode().getType() == 1) {
			//inte ok att flytta hit		
		}
			
	}*/
	
	public void doNodeWalker(GridNode currentNode, GridNode previousNode, int diceValue)
	{
		mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
		path = new ArrayList<GridNode>();
		nodeWalker(path, currentNode,  previousNode,  diceValue);

	}
	
	
	private void nodeWalker(ArrayList<GridNode> path, GridNode currentNode, GridNode previousNode, int diceValue) {
		
		
		// Sparar noden man står på i nuvarande path
		path.add(currentNode);
		
		// Tar fram alla möjliga noder man kan gå till från currentNode
		//Log.i("nodeWalker"," about to get next nodes");
		ArrayList<GridNode> nextNodes = this.getNextNodes(currentNode, previousNode);
		//Log.i("nodeWalker"," yey, i got all next nodes");
		
		// Kolla gameobjects
		if(nextNodes.size() != 0 && this.canStopHere(currentNode))
		{
			Log.i("nodeWalker"," yey, i can stop here!");
			// Save the node
			//Log.i("canStopHere",currentNode.getGameObject().getClass().toString());
			mPossiblePaths.add((ArrayList<GridNode>) path.clone());
			return;
		}
		
		// Kolla nodetypes, spara undan om det är ett tillåtet hus
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
			Log.i("nodeWalker"," yey, my dice is 0 so i stop for a coffee");
			mPossiblePaths.add((ArrayList<GridNode>) path.clone());

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
			this.nodeWalker(path, nextNode, currentNode, diceValue-1);
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
	
	public void drawHighlightSquare(Canvas canvas, int OffsetX, int OffsetY)
	{
		Paint paint = new Paint();
		
		ArrayList<GridNode> endSquares = new ArrayList<GridNode>();
		ArrayList<GridNode> normalSquares = new ArrayList<GridNode>();
		
		for(ArrayList<GridNode> paths : mPossiblePaths)
		{
			endSquares.add(paths.get(paths.size() - 1));
		}
		
		for(ArrayList<GridNode> paths : mPossiblePaths)
		{
			for(GridNode node : paths)
			{
				if(!normalSquares.contains(node) && !endSquares.contains(node))
				{
					normalSquares.add(node);
				}
			}
		}
		
		paint.setARGB(128, 0, 255, 0);
		for(GridNode node : endSquares)
		{
			int xPos = node.getPixelX();
			int yPos = node.getPixelY();
			canvas.drawCircle((float)Math.random()*2 + xPos+OffsetX+Grid.GRID_SIZE/2, (float)Math.random()*2 + yPos+OffsetY+Grid.GRID_SIZE/2, Grid.GRID_SIZE/2, paint);
		}
		
		paint.setARGB(110, 0, 10, 200);
		for(GridNode node : normalSquares)
		{
			int xPos = node.getPixelX();
			int yPos = node.getPixelY();
			canvas.drawCircle(xPos+OffsetX+Grid.GRID_SIZE/2, yPos+OffsetY+Grid.GRID_SIZE/2, Grid.GRID_SIZE/2.5f, paint);
		}
		/*

			//Rect rectangle = new Rect();
			//rectangle.set(xPos-OffsetX, yPos-OffsetY, xPos-OffsetX+Grid.GRID_SIZE, yPos-OffsetY+Grid.GRID_SIZE);
			//canvas.drawRect(rectangle, paint);
			canvas.drawCircle(xPos+OffsetX+Grid.GRID_SIZE/2, yPos+OffsetY+Grid.GRID_SIZE/2, Grid.GRID_SIZE/2, paint);

		*/
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
	
	public void setMovePath(ArrayList<GridNode> path)
	{
		this.mMovePath = (ArrayList<GridNode>) path.clone();
	}
	
	public ArrayList<GridNode> getMovePath()
	{
		return mMovePath;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setObjectMoney(int objectMoney) {
		this.objectMoney = objectMoney;
	}

	public int getObjectMoney() {
		return objectMoney;
	}
		
	public boolean equals(GameObject go)
	{
		if(go == null)
			return false;
		
		if(this.getName() == go.getName())
			return true;
		
		return false;
	}
}
	

