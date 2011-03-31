package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PlayState implements GameState
{
	protected Grid grid;
    protected CopObject cop;
    protected ThiefObject thief;
    protected PlayOrderState currentState;
    protected PlayOrderState copMoveState;
    protected PlayOrderState copTurnState;
    protected PlayOrderState thiefTurnState;
	
	private Context mContext;
	private Bitmap mBackgroundImage;

	//:
	public PlayState(Context context)
	{
		grid = new Grid(context);

		cop = new CopObject(grid.gridArray[2][4]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		thief = new ThiefObject(grid.gridArray[4][7]);
		copTurnState = new CopTurnState(this, cop, thief, grid);
		copMoveState = new CopMoveState(this, cop, thief, grid);
		thiefTurnState = new ThiefTurnState(this, cop, thief, grid);
		this.currentState = copTurnState;
		
		Resources res = context.getResources();       
        mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.lofi_map);
	}
	
	public void handleState(Canvas canvas)
	{
		currentState.handleState();
		currentState = currentState.getNextState();
		this.draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		//gt.currentState = this;
	}
	
	private void draw(Canvas c)
	{

		c.drawBitmap(mBackgroundImage, 0, 0, null);

		cop.doDraw(c);
		thief.doDraw(c);
	}
	
	//Denna metoden ska göras abstrakt här och sedan implementeras i de underliggande staten. (copTurnState, thiefRollState osv.)
	/*public void moveTo(float x, float y)
	{
		int column = (int)Math.floor(x/48); //48 är "lagom" storlek för punkterna som ritas ut
		int row = (int)Math.floor(y/48);
		cop.moveTo(grid.gridArray[column][row]);
	}*/
	
	//public void moveTo(float x, float y);
	
	//movement of a game object
	public void doTouch(View v, MotionEvent event)
	{
		Log.i("test1", "playstate dotouch");
		currentState.doTouch(v, event);
		
		//moveTo(x, y);		//moveTo ska lämpligen anropas med currentState och ska vara implementerad ide underliggande staten. (copTurnState, thiefRollState osv.)
	}
	
	protected CopTurnState getCopTurnState()
	{
		return (CopTurnState) copTurnState;
	}
}
