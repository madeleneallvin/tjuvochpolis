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
	private int pocketMoney;
	private int objectIndex;
	private String name;
	private ArrayList<GridNode> path = new ArrayList<GridNode>();
	private ArrayList<ArrayList<GridNode>> mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
	
	private float mDrawXPos;
	private float mDrawYPos;
	protected int moveToColCoordinate;
	protected int moveToRowCoordinate;
	protected boolean isMoving = true;
	
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
		nodeWalker(path, currentNode,  previousNode,  diceValue);
	}
	
	
	private void nodeWalker(ArrayList<GridNode> path, GridNode currentNode, GridNode previousNode, int diceValue) {
		
		
		// Sparar noden man st�r p� i nuvarande path
		path.add(currentNode);
		
		// Tar fram alla m�jliga noder man kan g� till fr�n currentNode
		ArrayList<GridNode> nextNodes = this.getNextNodes(currentNode, previousNode);
		
		
		// Kolla gameobjects
	/*	if(nextNodes.size() != 0 && this.canStopHere(currentNode))
		{
			// Save the node
			Log.i("canStopHere",currentNode.getGameObject().getClass().toString());
		}*/
		/*
		// Kolla nodetypes, spara undan om det �r ett till�tet hus
		if(this.isWalkable(currentNode))
		{
			// Save the node
		}
		*/
		//Om t�rningen visar 0, l�gg till aktuella noden, och hoppa ur.
		if(diceValue == 0) {
			// Sparar undan en m�jlig v�g
			mPossiblePaths.add(path);
			//Skriver ut alla paths
		//	for(int i = 0; i < path.size(); i++)
			//{
				//Log.i("Path", i + ": col=" + path.get(i).getNodeX() + " row=" + path.get(i).getNodeY());
		//	}
			// Rensar v�gen
			//path.remove(path.size()-1);
			return;
		}
		//Om det inte finns n�gra nextNodes s� st�r man i en �terv�ndsgr�nd
		if(nextNodes.size() == 0) {
			path.remove(path.size()-1);
			//return;
		}
		
		// loopar alla m�jliga v�gar
		for(GridNode nextNode : nextNodes) {
			// Anropar nodeWalker igen med ett mindre t�rningsv�rde och n�sta nod
			this.nodeWalker(path, nextNode, currentNode, diceValue-1);
		}
		
		//path.remove(path.size()-1);
		return;
	}
	
	
	private ArrayList<GridNode> getNextNodes(GridNode currentNode, GridNode previousNode){
		// h�mta alla noder runt current
		// ta inte med previous!
		ArrayList<GridNode> possibleNextNodes = new ArrayList<GridNode>();

		//kollar om �vre nod inte �r samma som f�reg�ende nod och att den g�r att g� p� 
		if(currentNode.getUpNode() != null && !currentNode.getUpNode().equals(previousNode) && this.isWalkable(currentNode.getUpNode())) {
			possibleNextNodes.add(currentNode.getUpNode());
		}
		
		//kollar om nedre nod inte �r samma som f�reg�ende nod och att den g�r att g� p�
		if(currentNode.getDownNode() != null && !currentNode.getDownNode().equals(previousNode) && this.isWalkable(currentNode.getDownNode())) {
			possibleNextNodes.add(currentNode.getDownNode());
		}
		
		//kollar om v�nstra nod inte �r samma som f�reg�ende nod och att den g�r att g� p�
		if(currentNode.getLeftNode() != null && !currentNode.getLeftNode().equals(previousNode) && this.isWalkable(currentNode.getLeftNode())) {
			possibleNextNodes.add(currentNode.getLeftNode());
		}
		
		//kollar om h�gra nod inte �r samma som f�reg�ende nod och att den g�r att g� p�
		if(currentNode.getRightNode() != null && !currentNode.getRightNode().equals(previousNode) && this.isWalkable(currentNode.getRightNode())) {
			possibleNextNodes.add(currentNode.getRightNode());
		}
		
		return possibleNextNodes;
	}
	
	//vad h�nde h�r :O
	
	public void moveToCoordinates(int rowCoordinate, int colCoordinate)
	{
		this.moveToRowCoordinate = rowCoordinate;
		this.moveToColCoordinate = colCoordinate;
		
		isMoving = true;
	}
	
	public void drawHighlightSquare(Canvas canvas, int OffsetX, int OffsetY){
		Paint paint = new Paint();
		paint.setARGB(128, 0, 255, 0);
		//Log.i("Y U NO WORK HERE???",  "" +mPossiblePaths.size() );
			for(int i = 0; i < mPossiblePaths.size(); i++)
			{//Log.i("test1", "Y U NO WORK???" + mPossiblePaths.size());
				int tempjSize = mPossiblePaths.get(i).size();
				//Log.i("Y U NO WORK HERE???",  "" +tempjSize );
				for(int j=0; j<tempjSize; j++)
				{
					int xPos = getPossiblePaths().get(i).get(j).getPixelX();
					int yPos = getPossiblePaths().get(i).get(j).getPixelY();

					//Rect rectangle = new Rect();
					//rectangle.set(xPos-OffsetX, yPos-OffsetY, xPos-OffsetX+Grid.GRID_SIZE, yPos-OffsetY+Grid.GRID_SIZE);
					//canvas.drawRect(rectangle, paint);
					canvas.drawCircle(xPos+OffsetX+Grid.GRID_SIZE/2, yPos+OffsetY+Grid.GRID_SIZE/2, Grid.GRID_SIZE/2, paint);
					
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

	 public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
	
