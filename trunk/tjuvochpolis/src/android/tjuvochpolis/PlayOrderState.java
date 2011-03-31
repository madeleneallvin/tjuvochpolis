package android.tjuvochpolis;

import android.view.MotionEvent;
import android.view.View;

public abstract class PlayOrderState {
	PlayState ps;
	CopObject cop;
	ThiefObject thief;
	Grid grid;
	
	public PlayOrderState(PlayState ps, CopObject cop, ThiefObject thief, Grid grid){
		this.cop = cop;
		this.thief = thief;
		this.grid = grid;
		this.ps = ps;
	}
	public abstract void handleState();
	
	public abstract void doTouch(View v, MotionEvent event);
	
	public abstract PlayOrderState getNextState();
}
