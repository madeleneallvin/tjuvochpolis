package android.tjuvochpolis;

import java.lang.*;

public class GridNode {

	private int x;
	private int y;
	public int nodeX;
	public int nodeY;
	private GridNode upNode;
	private GridNode downNode;
	private GridNode leftNode;
	private GridNode rightNode;
	private int type; //kanske inte int 
	private GameObject gameObject;
	private boolean nodeState;
	
	public static int STREET = 0;
	public static int HOUSE = 1;
	public static int POLICE_STATION = 2;
	public static int THIEF_NEST = 3;
	public static int BANK = 4;
	public static int TELEGRAPH = 5;
	
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
	void setPixelX(int x) {
		this.x = x;
	}
	int getPixelX() {
		return x;
	}
	void setPixelY(int y) {
		this.y = y;
	}
	int getPixelY() {
		return y;
	}
	void setNodeByX(int x) {
		x = (int) Math.floor(x/48.0);
		this.nodeX = x;
	}
	int getNodeByX(int x) {
		nodeX = (int) Math.floor(x/48.0);
		return nodeX;
	}
	void setNodeByY(int y) {
		y = (int) Math.floor(y/48.0);
		this.nodeY = y;
	}
	int getNodeByY(int y) {
		nodeY = (int) Math.floor(y/48.0);
		return nodeY;
	}
	int getNodeX() {
		int currentNodeX = (int) Math.floor(this.x/48.0);
		return currentNodeX;
	}
	int getNodeY() {
		int currentNodeY = (int) Math.floor(this.y/48.0);
		return currentNodeY;
	}
}
