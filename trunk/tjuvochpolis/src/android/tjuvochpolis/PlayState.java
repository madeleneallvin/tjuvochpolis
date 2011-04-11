package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.util.FloatMath;

public class PlayState implements GameState
{
	public static int MAX_FPS = 30;
	protected Grid mGrid;
    protected CopObject cop;
    protected ThiefObject thief;
    
    protected PlayOrderState mCurrentState;
    
    private PlayOrderState copRollDiceState;
    protected PlayOrderState copTurnState;
    protected PlayOrderState copMoveState;
    
    private PlayOrderState thiefRollDiceState;
    protected PlayOrderState thiefTurnState;
    protected PlayOrderState thiefMoveState;
    
    protected int mFrame;
    
    private float mPrevX;
    private float mPrevY;
    
    private float mZoom;
    private float mNewDistance;
    private float mPrevDistance;
    
    private int mOffsetX;
    private int mOffsetY;
    
	private Context mContext;
	private Bitmap mBackgroundImage;

	private long mLastTap = 0;
	private boolean zoomOut = false;
	
	public PlayState(Context context)
	{
		mGrid = new Grid(context);

		mOffsetX = 0;
		mOffsetY = 0;
		
		mPrevDistance = 0.0f;		
		mZoom = 1.0f;
		
		cop = new CopObject(mGrid.mGridArray[2][3]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		cop.setDrawXPos(cop.getParentNode().getPixelX());
		cop.setDrawYPos(cop.getParentNode().getPixelY());
		thief = new ThiefObject(mGrid.mGridArray[4][7]);   
		
		copTurnState = new CopTurnState(this, cop, thief, mGrid);
		copMoveState = new CopMoveState(this, cop, thief, mGrid);
		copRollDiceState =new CopRollDiceState(this, cop, thief, mGrid);
		
		thiefRollDiceState = new ThiefRollDiceState(this, cop, thief, mGrid);
		
		thiefTurnState = new ThiefTurnState(this, cop, thief, mGrid);
		thiefMoveState = new ThiefMoveState(this,cop,thief,mGrid);
		
		this.mCurrentState = getCopRollDiceState();
		
		Resources res = context.getResources();       
        mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.map_small);
        
        mFrame = 0;
	}
	
	public void handleState(Canvas canvas)
	{
		if(mFrame++ == MAX_FPS){
			mFrame = 1;
		}

		mCurrentState.handleState(mFrame);
		mCurrentState = mCurrentState.getNextState();
		this.draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
	}
	
	public void draw(Canvas c)
	{
		
		c.scale(mZoom, mZoom);
		c.drawBitmap(mBackgroundImage, mOffsetX, mOffsetY, null);
		
		mCurrentState.doDraw(c);
		
		cop.doDraw(c, mOffsetX, mOffsetY);
		thief.doDraw(c, mOffsetX, mOffsetY);
		
		//thief.drawHighlightSquare(c, mOffsetX, mOffsetY);
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
				long timediff = System.currentTimeMillis() - mLastTap;
				boolean doubleTap = timediff < 400;
				mLastTap = System.currentTimeMillis();
				
				if(doubleTap)
				{
					if(zoomOut)
					{
						mZoom = 1.0f;
						zoomOut = false;
					}
					else
					{
						zoomOut = true;
						mZoom = v.getHeight() * 1.0f / mBackgroundImage.getHeight();
					}
					mLastTap = 0;
				}
				
				Log.i("timediff", "" + timediff);
				
				mPrevX = x;
				mPrevY = y;

				mCurrentState.doTouch(v, event);
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
					else if(mOffsetX < (int) -(mBackgroundImage.getWidth()*mZoom - v.getWidth()))
						mOffsetX =(int) -(mBackgroundImage.getWidth()*mZoom - v.getWidth());
					
					if(mOffsetY > 0)
						mOffsetY = 0;
					else if(mOffsetY < (int) -(Math.ceil(mBackgroundImage.getHeight()*mZoom) - v.getHeight()))
						mOffsetY = (int) -(Math.ceil(mBackgroundImage.getHeight()*mZoom) - v.getHeight());
					
					mPrevX = x;
					mPrevY = y;
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

	protected void setThiefRollDiceState(PlayOrderState thiefRollDiceState) {
		this.thiefRollDiceState = thiefRollDiceState;
	}

	protected PlayOrderState getThiefRollDiceState() {
		return thiefRollDiceState;
	}

	protected void setCopRollDiceState(PlayOrderState copRollDiceState) {
		this.copRollDiceState = copRollDiceState;
	}

	protected PlayOrderState getCopRollDiceState() {
		return copRollDiceState;
	}
}
