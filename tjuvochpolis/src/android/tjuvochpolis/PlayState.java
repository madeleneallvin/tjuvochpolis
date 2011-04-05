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
	public static int MAX_FPS = 30;
	protected Grid grid;
    protected CopObject cop;
    protected ThiefObject thief;
    protected PlayOrderState currentState;
    protected PlayOrderState copMoveState;
    protected PlayOrderState copTurnState;
    protected PlayOrderState copRollDiceState;
    protected PlayOrderState thiefTurnState;
	
    
    protected int mFrame;
    
    private float PrevX;
    private float PrevY;
    
    
    private float zoom;
    private float prevDistance;
    
    private int OffsetX;
    private int OffsetY;
    
	private Context mContext;
	private Bitmap mBackgroundImage;

	//:
	public PlayState(Context context)
	{
		grid = new Grid(context);

		OffsetX = 0;
		OffsetY = 0;
		
		prevDistance = 0.0f;		
		zoom = 1.0f;
		
		cop = new CopObject(grid.gridArray[2][4]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		cop.mDrawXPos = cop.parentNode.getPixelX();
		cop.mDrawYPos = cop.parentNode.getPixelY();
		thief = new ThiefObject(grid.gridArray[4][7]);   
		copTurnState = new CopTurnState(this, cop, thief, grid);
		copMoveState = new CopMoveState(this, cop, thief, grid);
		copRollDiceState = new CopRollDiceState(this, cop, thief, grid);
		thiefTurnState = new ThiefTurnState(this, cop, thief, grid);
		
		this.currentState = copRollDiceState;
		
		Resources res = context.getResources();       
        mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.map_small);
        
        mFrame = 0;
	}
	
	public void handleState(Canvas canvas)
	{
		if(mFrame++ == MAX_FPS){
			mFrame = 1;
		}
	
		currentState.handleState(mFrame);
		currentState = currentState.getNextState();
		this.draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		//gt.currentState = this;
	}
	
	private void draw(Canvas c)
	{
		c.scale(zoom, zoom);
		
		c.drawBitmap(mBackgroundImage, OffsetX, OffsetY, null);
		
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
		//Log.i("test1", "playstate dotouch");
		
		float x = event.getX();
		float y = event.getY();
		
		int eventaction = event.getAction();
		switch(eventaction)
		{
			case MotionEvent.ACTION_DOWN:
			{
				PrevX = x;
				PrevY = y;
				currentState.doTouch(v, event);
				
				if(event.getPointerCount() == 2)
				{
					prevDistance = (event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)); 
				}
			}	
			break;
			case MotionEvent.ACTION_MOVE:
			{
				if(event.getPointerCount() == 1)
				{	
					OffsetX -= PrevX - x;
					OffsetY -= PrevY - y;
					
					if(OffsetX > 0)
						OffsetX = 0;
					else if(OffsetX < -(mBackgroundImage.getWidth() - v.getWidth()))
						OffsetX = -(mBackgroundImage.getWidth() - v.getWidth());
					
					if(OffsetY > 0)
						OffsetY = 0;
					else if(OffsetY < -(mBackgroundImage.getHeight() - v.getHeight()))
						OffsetY = -(mBackgroundImage.getHeight() - v.getHeight());
					
					PrevX = x;
					PrevY = y;
				}
				else
				{
					float newDistance = (event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1));
					if(newDistance > prevDistance)
						zoom = 1.0f;
					else 
					{
						zoom = v.getHeight() * 1.0f / mBackgroundImage.getHeight();
						Log.i("zoom", "" + zoom);
					}
						
					prevDistance = newDistance;
				}
				
			}
			break;
		}
		
		//moveTo(x, y);		//moveTo ska lämpligen anropas med currentState och ska vara implementerad ide underliggande staten. (copTurnState, thiefRollState osv.)
	}
	
	protected CopTurnState getCopTurnState()
	{
		return (CopTurnState) copTurnState;
	}
}
