package android.tjuvochpolis;

import android.util.Log;

public class PoliceStationObject extends GameStaticObject {

	public PoliceStationObject(String name, GridNode parentNode) {
		super(name, parentNode);
		this.getParentNode().setGameStaticObject(this);
	}

	@Override
	public void handleEvent() {
		Log.i("PoliceStationObject", "You are on the police station.");

	}

}
