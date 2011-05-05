package android.tjuvochpolis;

import android.util.Log;

public class PowerupObject extends GameStaticObject{

	public PowerupObject(String name, GridNode parentNode) {
		super(name, parentNode);
	}

	@Override
	public void handleEvent() {
		Log.i("PowerupObject", "You are on a power up");
		
	}
}
