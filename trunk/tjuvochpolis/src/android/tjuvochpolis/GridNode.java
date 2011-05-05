package android.tjuvochpolis;

import android.view.MotionEvent;

public class GridNode {

	private int mRow;
	private int mCol;
	
	private GridNode mUpNode;
	private GridNode mDownNode;
	private GridNode mLeftNode;
	private GridNode mRightNode;
	private int mType;
	private GameObject mGameObject;
	private GameStaticObject mGameStaticObject;

	public static int STREET = 0;
	public static int HOUSE = 1;
	public static int POLICE_STATION = 2;
	public static int THIEF_NEST = 3;
	public static int BANK = 4;
	public static int TELEGRAPH = 5;
	
	public GridNode(int row, int col) {
		mRow = row;
		mCol = col;
	}
	
	public String toString() {
		return "Row:" + this.getRow() + " Col:" + this.getCol();
	}
	
	public void setUpNode(GridNode upNode) {
		this.mUpNode = upNode;
	}
	
	public GridNode getUpNode() {
		return mUpNode;
	}
	
	public void setDownNode(GridNode downNode) {
		this.mDownNode = downNode;
	}
	
	public GridNode getDownNode() {
		return mDownNode;
	}
	
	public void setLeftNode(GridNode leftNode) {
		this.mLeftNode = leftNode;
	}
	
	public GridNode getLeftNode() {
		return mLeftNode;
	}
	
	public void setRightNode(GridNode rightNode) {
		this.mRightNode = rightNode;
	}
	
	public GridNode getRightNode() {
		return mRightNode;
	}
	
	public void setType(int type) {
		this.mType = type;
	}
	
	public int getType() {
		return mType;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.mGameObject = gameObject;
	}
	
	public GameObject getGameObject() {
		return mGameObject;
	}
	
	public void setGameStaticObject(GameStaticObject gameStaticObject) {
		this.mGameStaticObject = gameStaticObject;
	}

	public GameStaticObject getGameStaticObject() {
		return mGameStaticObject;
	}
	
	// return the nodes pixel position
	public int getX() {
		return mCol * Grid.GRID_SIZE;
	}
	// return the nodes pixel position
	public int getY() {
		return mRow * Grid.GRID_SIZE;
	}
	
	public int getRow() {
		return mRow;
	}
	public int getCol() {
		return mCol;
	}
	
	public static int getRow(MotionEvent event, int offset)
	{
		return ((int) event.getY() - offset) / Grid.GRID_SIZE;
	}
	
	public static int getCol(MotionEvent event, int offset)
	{
		return ((int) event.getX() - offset) / Grid.GRID_SIZE;
	}
	
}
