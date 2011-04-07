package android.tjuvochpolis;

public class GridNode {

	private int mX;
	private int mY;
	private int mNodeX;
	private int mNodeY;
	private GridNode mUpNode;
	private GridNode mDownNode;
	private GridNode mLeftNode;
	private GridNode mRightNode;
	private int mType; //kanske inte int 
	private GameObject mGameObject;
	private boolean mNodeState;
	
	public static int STREET = 0;
	public static int HOUSE = 1;
	public static int POLICE_STATION = 2;
	public static int THIEF_NEST = 3;
	public static int BANK = 4;
	public static int TELEGRAPH = 5;
	
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
	public void setNodeState(boolean nodeState) {
		this.mNodeState = nodeState;
	}
	public boolean isNodeState() {
		return mNodeState;
	}
	public void setPixelX(int x) {
		this.mX = x;
	}
	public int getPixelX() {
		return mX;
	}
	public void setPixelY(int y) {
		this.mY = y;
	}
	public int getPixelY() {
		return mY;
	}
	public void setNodeByX(int x) {
		this.mNodeX = x/Grid.GRID_SIZE;
	}
	public int getNodeByX(int x) {
		return x/Grid.GRID_SIZE;
	}
	public void setNodeByY(int y) {
		this.mNodeY = y/Grid.GRID_SIZE;
	}
	public int getNodeByY(int y) {
		return y/Grid.GRID_SIZE;
	}
	public int getNodeX() {
		return getNodeByX(this.mX);
	}
	public int getNodeY() {
		return getNodeByY(this.mY);
	}
}
