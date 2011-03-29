package android.tjuvochpolis;

public class GridNode {

	private int id;
	private GridNode upNode;
	private GridNode downNode;
	private GridNode leftNode;
	private GridNode rightNode;
	private int type; //kanske inte int 
	
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
	void setId(int id) {
		this.id = id;
	}
	int getId() {
		return id;
	}
	
	
}
