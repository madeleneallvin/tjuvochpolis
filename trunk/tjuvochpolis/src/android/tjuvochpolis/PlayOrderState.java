package android.tjuvochpolis;

public abstract class PlayOrderState {
	CopObject cop;
	ThiefObject thief;
	Grid grid;
	
	public PlayOrderState(CopObject cop, ThiefObject thief, Grid grid){
		this.cop = cop;
		this.thief = thief;
		this.grid = grid;
	}
}
