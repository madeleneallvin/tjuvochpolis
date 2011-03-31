package android.tjuvochpolis;

public class GridNode {

	private int x;
	private int y;
	private GridNode upNode;
	private GridNode downNode;
	private GridNode leftNode;
	private GridNode rightNode;
	private int type; //kanske inte int 
	private GameObject gameObject;
	private boolean nodeState;
	
	void setUpNode(GridNode upNode) {
		this.upNode = upNode;
	}
	GridNode getUpNode() {
		return upNode;
	}
	void setDownNode(GridNode downNode) {
		this.downNode = downNode;
	}
	GridNode getDownNode() {
		return downNode;
	}
	void setLeftNode(GridNode leftNode) {
		this.leftNode = leftNode;
	}
	GridNode getLeftNode() {
		return leftNode;
	}
	void setRightNode(GridNode rightNode) {
		this.rightNode = rightNode;
	}
	GridNode getRightNode() {
		return rightNode;
	}
	void setType(int type) {
		this.type = type;
	}
	int getType() {
		return type;
	}
	
	void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	GameObject getGameObject() {
		return gameObject;
	}
	void setNodeState(boolean nodeState) {
		this.nodeState = nodeState;
	}
	boolean isNodeState() {
		return nodeState;
	}
	void setX(int x) {
		this.x = x;
	}
	int getX() {
		return x;
	}
	void setY(int y) {
		this.y = y;
	}
	int getY() {
		return y;
	}
	
	
}
