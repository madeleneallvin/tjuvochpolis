package android.tjuvochpolis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameStaticObject {

	private GridNode mParentNode;
	private int objectMoney;
	private String name;

	public GameStaticObject(String name, GridNode parentNode) {
		this.setParentNode(parentNode);
		this.setName(name);
		
		this.getParentNode().setGameStaticObject(this);
	}
	
	public abstract boolean handleEvent(MotionEvent e, Context context);
	
	public abstract void drawSplashScreen(Canvas c, float mZoom, Context context);

	
	
	
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
	
	public void saveState(Context mContext) {
		SharedPreferences mPrefs = mContext.getSharedPreferences("gamePrefs", Context.MODE_WORLD_READABLE);
		
		SharedPreferences.Editor ed = mPrefs.edit();
		ed.putInt(name + "_objectStaticMoney", objectMoney);
		ed.commit();
	}
}
