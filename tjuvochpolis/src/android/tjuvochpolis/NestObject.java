package android.tjuvochpolis;


public class NestObject extends GameStaticObject {

	public NestObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.setObjectMoney(0);
	}

	public void storeMoney(int money){
		this.setObjectMoney(this.getObjectMoney() + money);
	}
	
	public int takeMoney(){
		return this.getObjectMoney();
	}
	
}
