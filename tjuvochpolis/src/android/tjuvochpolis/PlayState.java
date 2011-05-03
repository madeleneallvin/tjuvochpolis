package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PlayState implements GameState
{
	public static int MAX_FPS = 30;
	protected Grid mGrid;
	
	protected ArrayList<GameObject> mObjectArray;
	protected ArrayList<GameStaticObject> mObjectStaticArray;
    
    public enum mObjectIndex {
        COP1 (0),
        COP2 (1),
        THIEF1(2),
        THIEF2 (3);
               
        private final int index;
        mObjectIndex(int index){
        	this.index = index;
        }
		public int getIndex() {
			return index;
		}
    }
    
    public enum mObjectStaticIndex {
		BANK1 (0),
		NEST1 (1);
		
		private final int index;
		mObjectStaticIndex(int index) {
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
		
		SharedPreferences mPrefs = context.getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		
		mObjectArray = new ArrayList<GameObject>();
//		cop = new CopObject(mGrid.mGridArray[2][3]);		//positionen ska kontrolleras av vart tjuvn�ste och polisstation ligger
		mObjectArray.add(new CopObject("COP1",mGrid.mGridArray[mPrefs.getInt("COP1_col",2)][mPrefs.getInt("COP1_row",3)]));
		mObjectArray.add(new CopObject("COP2",mGrid.mGridArray[mPrefs.getInt("COP2_col",3)][mPrefs.getInt("COP2_row",2)]));
		mObjectArray.add(new ThiefObject("THIEF1",mGrid.mGridArray[mPrefs.getInt("THIEF1_col",4)][mPrefs.getInt("THIEF1_row",7)]));
		mObjectArray.add(new ThiefObject("THIEF2",mGrid.mGridArray[mPrefs.getInt("THIEF2_col",5)][mPrefs.getInt("THIEF2_row",7)]));

		//GameStaticObjects
		mObjectStaticArray = new ArrayList<GameStaticObject>();
		
		//mObjectArray.add(new BankObject("BANK1",mGrid.mGridArray[mPrefs.getInt("BANK1_col",5)][mPrefs.getInt("BANK1_row",4)]));
		//mObjectArray.add(new BankObject("NEST1",mGrid.mGridArray[mPrefs.getInt("NEST1_col",2)][mPrefs.getInt("NEST1_row",1)]));
		
	
		// Create the states
		copTurnState = new CopTurnState(this, mObjectArray, mObjectStaticArray, mGrid);
		copMoveState = new CopMoveState(this, mObjectArray, mObjectStaticArray, mGrid);
		copRollDiceState = new CopRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid);

		thiefRollDiceState = new ThiefRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid);
		thiefTurnState = new ThiefTurnState(this, mObjectArray, mObjectStaticArray, mGrid);
		thiefMoveState = new ThiefMoveState(this, mObjectArray, mObjectStaticArray, mGrid);
		
		// Set current state
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
		
		//Denna m�ste sparas d� mCurrentState blir ett nytt objekt nedanf�r
		String currentObjectSelected = mCurrentState.getCurrentObjectSelected();

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
		c.drawBitmap(mBackgroundImage, mOffsetX, getOffsetY(), null);
		
		mCurrentState.doDraw(c, mZoom);
		
		mObjectArray.get(mObjectIndex.COP1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.COP2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF1.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		mObjectArray.get(mObjectIndex.THIEF2.getIndex()).doDraw(c, mOffsetX, mOffsetY);
		
		//this.drawHud(c,mZoom);

	}
	
	
	//movement of a game object
	public void doTouch(View v, MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		int eventaction = event.getAction();
		
		switch(eventaction) {
			case MotionEvent.ACTION_DOWN: {
				mStartX = x;
				mStartY = y;
				distance = 0.0f;

				moveAction = false;
				long timediff = System.currentTimeMillis() - mLastTap;
				boolean doubleTap = timediff < 400;
				mLastTap = System.currentTimeMillis();
	
				if(doubleTap) {	
					if(zoomOut) {	
						mOffsetX = (int) -(x/mZoom - mOffsetX - v.getWidth()/2.0f);
						mOffsetY = (int) -(y/mZoom - getOffsetY() - v.getHeight()/2.0f);
	
						mZoom = 1.0f;
	
						zoomOut = false;
					}
					else {
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
	
	public void saveState()
	{
		Log.i("PlayState", "savestate");
		
		for(int i = 0; i < mObjectArray.size(); i++)
		{
			mObjectArray.get(i).saveState(mContext);
		}
	}
	
}