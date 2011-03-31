package android.tjuvochpolis;

public class CopTurnState extends PlayOrderState {
	
	public CopTurnState(CopObject cop, ThiefObject thief, Grid grid){
		super(cop, thief, grid);
	}
	
	public void moveTo(float x, float y) {
		int column = (int)Math.floor(x/30); //30 är "lagom" storlek för punkterna som ritas ut
		int row = (int)Math.floor(y/30);
		this.cop.moveTo(this.grid.gridArray[column][row]);
	}
	
}
