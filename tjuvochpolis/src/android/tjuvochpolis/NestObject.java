package android.tjuvochpolis;

public class NestObject extends GameStaticObject {

	public NestObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(0);
	}

	/*
	 * Function to store away money in the nest
	 */
	public void storeMoney(int money) {
		this.setObjectMoney(this.getObjectMoney() + money);
	}
	
	/*
	 * Function to take all the money from the nest
	 */
	public int takeMoney() {
		int money = this.getObjectMoney();
		this.setObjectMoney(0);
		return money;
	}
}
