package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class PlayState implements GameState
{
	protected Grid grid;
    protected CopObject cop;
    protected ThiefObject thief;
    private PlayOrderState playOrderState;
	private PlayOrderState copTurnState;
	private PlayOrderState thiefTurnState;
	
	private Context mContext;
	private Bitmap mBackgroundImage;

	
	public PlayState(Context context)
	{
		grid = new Grid(context);

		cop = new CopObject(grid.gridArray[4][1]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		thief = new ThiefObject(grid.gridArray[4][7]);
		copTurnState = new CopTurnState(cop, thief, grid);
		thiefTurnState = new ThiefTurnState(cop, thief, grid);
		
		Resources res = context.getResources();       
        mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.lofi_map);
		
	}
	
	public void handleState(Canvas canvas)
	{
		this.draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		
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
		int column = (int)Math.floor(x/30); //30 är "lagom" storlek för punkterna som ritas ut
		int row = (int)Math.floor(y/30);
		cop.moveTo(grid.gridArray[column][row]);
	}*/
	
	//public void moveTo(float x, float y);
	
	//movement of a game object
	public void doTouch(View v, MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
				
		
		//moveTo(x, y);		//moveTo ska lämpligen anropas med currentState och ska vara implementerad ide underliggande staten. (copTurnState, thiefRollState osv.)
	}
}
