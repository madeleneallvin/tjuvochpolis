package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.util.FloatMath;

public class PlayState implements GameState
{
	public static int MAX_FPS = 30;
	protected Grid mGrid;
    //protected CopObject cop;
    //protected ThiefObject thief;
    
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
    private float mStartX;
    private float mStartY;
    
    private float mZoom;
    private float mNewDistance;
    private float mPrevDistance;
    
    private int mOffsetX;
    private int mOffsetY;
    private int prevOffsetX;
    private int prevOffsetY;
    private int orientation;
    
	private Context mContext;
	private Bitmap mBackgroundImage;
	

	private long mLastTap = 0;
	private boolean zoomOut = false;
	private boolean moveAction = false;
	private float distance = 0.0f;
	
	private int canvasH=0;
	
	public PlayState(Context context)
	{
		mGrid = new Grid(context);

		setContext(context);
		
		mOffsetX = 0;
		mOffsetY = 0;
		
		mPrevDistance = 0.0f;		
		mZoom = 1.0f;
		
		mObjectArray = new ArrayList<GameObject>();
//		cop = new CopObject(mGrid.mGridArray[2][3]);		//positionen ska kontrolleras av vart tjuvnäste och polisstation ligger
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

        //används för att hitta orientation på device.
        //Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();        
        //orientation = display.getOrientation();
        
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
		
		canvasH=canvas.getHeight();
	}
	
	public void nextState(GameThread gt)
	{
	}
	
	public void draw(Canvas c)
	{
		c.scale(mZoom, mZoom);
		
		/*if(orientation == 1) //liggande 
		{
			c.drawBitmap(mBackgroundImage, 48+mOffsetX, mOffsetY, null);
		}
		else if(orientation == 0)
		{*/
			c.drawBitmap(mBackgroundImage, mOffsetX, getOffsetY(), null);
		//}
		
		
		mObjectArray.get(mObjectIndex.COP1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.COP2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.BANK1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.NEST1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		
		mCurrentState.doDraw(c, mZoom);
		
		//this.drawHud(c,mZoom);

	}
	
	
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
				mStartX = x;
				mStartY = y;
				distance = 0.0f;
				
				Log.i("Action", "Down");
				moveAction = false;
				long timediff = System.currentTimeMillis() - mLastTap;
				boolean doubleTap = timediff < 400;
				mLastTap = System.currentTimeMillis();
				
				if(doubleTap)
				{	
					if(zoomOut)
					{	
						mOffsetX = (int) -(x/mZoom - mOffsetX - v.getWidth()/2.0f);
						mOffsetY = (int) -(y/mZoom - getOffsetY() - v.getHeight()/2.0f);
						
						mZoom = 1.0f;
						
						zoomOut = false;
					}
					else
					{
						prevOffsetX = mOffsetX;
						prevOffsetY = mOffsetY;
						mOffsetX = (int) -(x - mOffsetX - v.getWidth());
						mOffsetY = 0;
						
						zoomOut = true;
						
						if(v.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
							mZoom = v.getWidth() * 1.0f / mBackgroundImage.getWidth();
						else
							mZoom = v.getHeight() * 1.0f / mBackgroundImage.getHeight();
							
					}
					
					// Kolla offset kriterier
					if(mOffsetX > 0)
						mOffsetX = 0;
					else if(mOffsetX*mZoom < (int) -((mBackgroundImage.getWidth())*mZoom - v.getWidth()))
						mOffsetX = (int) -((mBackgroundImage.getWidth()*mZoom - v.getWidth())/mZoom);
						
					if(mOffsetY > (int) (96-96*mZoom))
						mOffsetY = (int) (96-96*mZoom);
					else if(mOffsetY < (int) - ((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom  + (48*2)/mZoom) + (int) (96-96*mZoom))
						mOffsetY = (int) -((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom + (48*2)/mZoom) + (int) (96-96*mZoom);
					
					mLastTap = 0;
				}
				
				//Log.i("timediff", "" + timediff);
				
				mPrevX = x;
				mPrevY = y;
				
				
			}
			break;
			case MotionEvent.ACTION_MOVE:
			{
				distance = (float) Math.sqrt((x-mStartX)*(x-mStartX) + (y-mStartY)*(y-mStartY)); 
			
				//Log.i("Offset", "" + mOffsetX);
				
				if(distance > 24.0f)
				{
					moveAction = true;
					
					if(event.getPointerCount() == 1)
					{	
						mOffsetX -= mPrevX - x;
						mOffsetY -= mPrevY - y;
						
					
						if(mOffsetX > 0)
							mOffsetX = 0;
						else if(mOffsetX*mZoom < (int) -((mBackgroundImage.getWidth())*mZoom - v.getWidth()))
							mOffsetX = (int) -((mBackgroundImage.getWidth()*mZoom - v.getWidth())/mZoom);
							
						if(mOffsetY > (int) (96-96*mZoom))
							mOffsetY = (int) (96-96*mZoom);
						else if(mOffsetY < (int) - ((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom  + (48*2)/mZoom) + (int) (96-96*mZoom))
							mOffsetY = (int) -((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom + (48*2)/mZoom) + (int) (96-96*mZoom);
						
						
					}
				}
				
				mPrevX = x;
				mPrevY = y;
			}
			break;
			case MotionEvent.ACTION_UP:
			{
				if(moveAction == false)
				{ 
					if(zoomOut != true)
						mCurrentState.doTouch(v, event);
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
		return mOffsetY+48;
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

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public Context getContext() {
		return mContext;
	}
}