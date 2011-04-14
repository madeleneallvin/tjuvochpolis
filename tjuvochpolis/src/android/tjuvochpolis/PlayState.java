package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.util.FloatMath;

public class PlayState implements GameState
{
	public static int MAX_FPS = 30;
	protected Grid mGrid;
 //   protected CopObject cop, cop2;
  //  protected ThiefObject thief, thief2;
    
    protected ArrayList<GameObject> mObjectArray;
  
    public enum mObjectIndex {
        COP1 (0),
        COP2 (1),
        THIEF1(2),
        THIEF2 (3),
        BANK1 (4),
        NEST1 (5);
               
        private final int index;
        mObjectIndex(int index){
        	this.index = index;
        }
        
       
        
        
		public int getIndex() {
			return index;
		}
    }
        
   

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
    
    private Paint mPaintText;
    private Paint mPaintBgLeft;
    private Paint mPaintBgRight;
    private Rect mRectLeft;
    private Rect mRectRight;
    
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
		
		
			
		
		 //positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
		

		mObjectArray = new ArrayList<GameObject>();
		
		mObjectArray.add(new CopObject("COP1",mGrid.mGridArray[2][3]));
		mObjectArray.add(new CopObject("COP2",mGrid.mGridArray[3][3]));
		mObjectArray.add(new ThiefObject("THIEF1",mGrid.mGridArray[4][7]));
		mObjectArray.add(new ThiefObject("THIEF2",mGrid.mGridArray[5][7]));
		mObjectArray.add(new BankObject("BANK1",mGrid.mGridArray[5][4]));
		mObjectArray.add(new BankObject("NEST1",mGrid.mGridArray[2][1]));
		
		
		
		//mObjectArray.get(mObjectIndex.COP1.getIndex());
		
	
		//copTurnState = new CopTurnState(this, cop, thief, mGrid);
		copTurnState = new CopTurnState(this, mObjectArray, mGrid);
		copMoveState = new CopMoveState(this, mObjectArray, mGrid);
		copRollDiceState =new CopRollDiceState(this, mObjectArray, mGrid);
		
		thiefRollDiceState = new ThiefRollDiceState(this, mObjectArray, mGrid);
		
		thiefTurnState = new ThiefTurnState(this, mObjectArray, mGrid);
		thiefMoveState = new ThiefMoveState(this, mObjectArray, mGrid);
		
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
		String currentObjectSelected = mCurrentState.getCurrentObjectSelected(); //DENNA MÅSTE SPARAS DÅ mCurrentState blir ett nytt objekt nedanför
		mCurrentState = mCurrentState.getNextState();
		mCurrentState.setCurrentObjectSelected(currentObjectSelected);
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
		
		mObjectArray.get(mObjectIndex.COP1.getIndex()).doDraw(c, mOffsetX, mOffsetY);;
		mObjectArray.get(mObjectIndex.COP2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.BANK1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.NEST1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		
		
		//drawing the hud
	
		

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
