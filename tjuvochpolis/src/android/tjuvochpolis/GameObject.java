package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;


public abstract class GameObject {

	private GridNode mParentNode;
	private int mCurrentDiceValue;
	private int objectMoney;
	private int objectIndex;
	private String name;
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

	public GameObject(String name,GridNode parentNode) {
		this.setParentNode(parentNode);
		this.name = name;

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

		// Sparar noden man står på i nuvarande path
		path.add(currentNode);

		// Tar fram alla möjliga noder man kan gå till från currentNode
		ArrayList<GridNode> nextNodes = this.getNextNodes(currentNode, previousNode);

		// Kolla gameobjects
		if(nextNodes.size() != 0 && this.canStopHere(currentNode))
		{
			// Save the node
			mPossiblePaths.add((ArrayList<GridNode>) path.clone());
			return;
		}

		//Om tärningen visar 0, lägg till aktuella noden, och hoppa ur.
		if(diceValue == 0) {
			// Sparar undan en möjlig väg
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


	public void moveToCoordinates(int rowCoordinate, int colCoordinate)
	{
		this.moveToRowCoordinate = rowCoordinate;
		this.moveToColCoordinate = colCoordinate;

		isMoving = true;
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

	public boolean equals(GameObject go){

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
	
		ed.putInt(name + "_row", (mParentNode.getPixelX())/48);
		ed.putInt(name + "_col", (mParentNode.getPixelY()-48)/48);
		ed.putInt(name + "_money", objectMoney);
		
		ed.commit();
	}
}


