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
	protected Grid mGrid;
    protected CopObject cop;
    protected ThiefObject thief;
    protected PlayOrderState currentState;
    protected PlayOrderState copMoveState;
    protected PlayOrderState thiefMoveState;
    protected PlayOrderState copTurnState;
    protected PlayOrderState copRollDiceState;
    protected PlayOrderState thiefTurnState;
	
    
    protected int mFrame;
    
    private float mPrevX;
    private float mPrevY;
    
    
    private float mZoom;
    private float mPrevDistance;
    
    private int mOffsetX;
    private int mOffsetY;
    
	private Context mContext;
	private Bitmap mBackgroundImage;

	//:
	public PlayState(Context context)
	{
		mGrid = new Grid(context);

		mOffsetX = 0;
		mOffsetY = 0;
		
		mPrevDistance = 0.0f;		
		mZoom = 1.0f;
		
		cop = new CopObject(mGrid.mGridArray[2][4]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		cop.setDrawXPos(cop.getParentNode().getPixelX());
		cop.setDrawYPos(cop.getParentNode().getPixelY());
		thief = new ThiefObject(mGrid.mGridArray[4][7]);   
		copTurnState = new CopTurnState(this, cop, thief, mGrid);
		copMoveState = new CopMoveState(this, cop, thief, mGrid);
		copRollDiceState = new CopRollDiceState(this, cop, thief, mGrid);
		thiefTurnState = new ThiefTurnState(this, cop, thief, mGrid);
		thiefMoveState = new ThiefMoveState(this,cop,thief,mGrid);
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
	
	public void draw(Canvas c)
	{
		c.scale(mZoom, mZoom);
		
		c.drawBitmap(mBackgroundImage, mOffsetX, mOffsetY, null);
		
		cop.doDraw(c, mOffsetX, mOffsetY);
		thief.doDraw(c, mOffsetX, mOffsetY);
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
				mPrevX = x;
				mPrevY = y;
				currentState.doTouch(v, event);
				
				if(event.getPointerCount() == 2)
				{
					mPrevDistance = (event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)); 
				}
			}	
			break;
			case MotionEvent.ACTION_MOVE:
			{
				if(event.getPointerCount() == 1)
				{	
					mOffsetX -= mPrevX - x;
					mOffsetY -= mPrevY - y;
					
					if(mOffsetX > 0)
						mOffsetX = 0;
					else if(mOffsetX < -(mBackgroundImage.getWidth() - v.getWidth()))
						mOffsetX = -(mBackgroundImage.getWidth() - v.getWidth());
					
					if(mOffsetY > 0)
						mOffsetY = 0;
					else if(mOffsetY < -(mBackgroundImage.getHeight() - v.getHeight()))
						mOffsetY = -(mBackgroundImage.getHeight() - v.getHeight());
					
					mPrevX = x;
					mPrevY = y;
				}
				else
				{
					float newDistance = (event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1));
					if(newDistance > mPrevDistance)
						mZoom = 1.0f;
					else 
					{
						mZoom = v.getHeight() * 1.0f / mBackgroundImage.getHeight();
						Log.i("zoom", "" + mZoom);
					}
						
					mPrevDistance = newDistance;
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
	protected ThiefTurnState getThiefTurnState()
	{
		
		return (ThiefTurnState) thiefTurnState;
	}
	
	public void setOffsetX(int offset)
	{
		mOffsetX = offset;
	}
	
	public void setOffsetY(int offset)
	{
		mOffsetY = offset;
	}
	
	public int getOffsetX()
	{
		return mOffsetX;
	}
	
	public int getOffsetY()
	{
		return mOffsetY;
	}
}
