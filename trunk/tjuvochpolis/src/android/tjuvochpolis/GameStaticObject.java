package android.tjuvochpolis;

public abstract class GameStaticObject {

	private GridNode mParentNode;
	private int objectMoney;
	private String name;

	public GameStaticObject(String name, GridNode parentNode) {
		this.setParentNode(parentNode);
		this.setName(name);
		this.getParentNode().setGameStaticObject(this);
	}

	public void setParentNode(GridNode parentNode) {
		this.mParentNode = parentNode;
	}

	public GridNode getParentNode() {
		return mParentNode;
	}

	public void setObjectMoney(int objectMoney) {
		this.objectMoney = objectMoney;
	}

	public int getObjectMoney() {
		return objectMoney;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
