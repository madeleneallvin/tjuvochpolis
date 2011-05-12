package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;


public abstract class GameObject {

	private GridNode mParentNode;
	private int mCurrentDiceValue;
	protected int objectMoney;
	private int objectIndex;
	protected String name;
	private ArrayList<GridNode> path = new ArrayList<GridNode>();
	private int mCurrentPathPosition = 0;
	private ArrayList<ArrayList<GridNode>> mPossiblePaths = new ArrayList<ArrayList<GridNode>>();
	private ArrayList<GridNode> mMovePath = new ArrayList<GridNode>();
	private float mDrawXPos;
	private float mDrawYPos;
	protected int moveToColCoordinate;
	protected int moveToRowCoordinate;
	protected boolean isMoving = true;
	protected boolean objectFinishedMoving = false;
	private int waitingLeft = 0;
	
	public GameObject(String name,GridNode parentNode, int mCurrentdiceValue, int objectMoney) {
		this.setParentNode(parentNode);
		this.name = name;
		
		this.mCurrentDiceValue = mCurrentdiceValue;
		this.objectMoney = objectMoney;
		
		this.getParentNode().setGameObject(this);
	}

	public abstract boolean isWalkable(GridNode node);
	public abstract void doDraw(Canvas canvas, int offsetX, int offsetY, Context context); 
	public abstract boolean hasMoney();
	

	public boolean canStopHere(GridNode node){
		return false;
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
		if(this.canStopHere(currentNode)) {
			// Save the node	
			mPossiblePaths.add((ArrayList<GridNode>) path.clone());
			
			//om det �r en �terv�ndsgr�nd
			if(nextNodes.size() == 0) {
				path.remove(path.size()-1);
				return;
			}
		}
		
		//Om t�rningen visar 0, l�gg till aktuella noden, och hoppa ur.
		if(diceValue == 0) {
			// Sparar undan en m�jlig v�g
			mPossiblePaths.add((ArrayList<GridNode>) path.clone());

			// Rensar v�gen
			path.remove(path.size()-1);
			return;
		}
		//Om det inte finns n�gra nextNodes s� st�r man i en �terv�ndsgr�nd
		if(nextNodes.size() == 0) {
			path.remove(path.size()-1);
			return;
		}

		// loopar alla m�jliga v�gar
		for(GridNode nextNode : nextNodes) {
			// Anropar nodeWalker igen med ett mindre t�rningsv�rde och n�sta nod
			this.nodeWalker(path, nextNode, currentNode, diceValue-1);
		}

		path.remove(path.size()-1);
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


	public void moveToCoordinates(int rowCoordinate, int colCoordinate)
	{
		this.moveToRowCoordinate = rowCoordinate;
		this.moveToColCoordinate = colCoordinate;

		isMoving = true;
	}
	public void transportToJail(int rowCoordinate, int colCoordinate, Grid mGrid){
		//this.moveToRowCoordinate = rowCoordinate;
		//this.moveToColCoordinate = colCoordinate;
		
		this.mDrawXPos = mGrid.GRID_SIZE*colCoordinate;
		this.mDrawYPos = mGrid.GRID_SIZE*rowCoordinate;
		
		this.getParentNode().setGameObject(null);
		mGrid.getGridNode(rowCoordinate, colCoordinate).setGameObject(this);
		this.setParentNode(mGrid.getGridNode(rowCoordinate, colCoordinate));
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

	public void setCurrentPathPosition(int mCurrentPathPosition) {
		this.mCurrentPathPosition = mCurrentPathPosition;
	}

	public int getCurrentPathPosition() {
		return mCurrentPathPosition;
	}

	public void saveState(Context mContext)
	{
		SharedPreferences mPrefs = mContext.getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
	
		SharedPreferences.Editor ed = mPrefs.edit();
	
		ed.putInt(name + "_row", (mParentNode.getRow()));
		ed.putInt(name + "_col", (mParentNode.getCol()));
		ed.putInt(name + "_money", objectMoney);
		ed.putInt(name + "_diceValue", mCurrentDiceValue);
		
		ed.commit();
	}

	public void setWaitingLeft(int waitingLeft) {
		this.waitingLeft = waitingLeft;
	}

	public int getWaitingLeft() {
		return waitingLeft;
	}
}
	

