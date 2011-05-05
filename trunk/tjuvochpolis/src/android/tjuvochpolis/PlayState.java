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

public class PlayState implements GameState {

	public static int MAX_FPS = 30;
	public static int HUD_TOP_HEIGHT = 48;
	public static int HUD_BOTTOM_HEIGHT = 48;
	
	protected Grid mGrid;
    
    protected ArrayList<GameObject> mObjectArray;
    protected ArrayList<GameStaticObject> mObjectStaticArray;
    
    public enum mObjectIndex {
        COP1 (0),
        COP2 (1),
        THIEF1(2),
        THIEF2 (3);
               
        private final int index;
        mObjectIndex(int index) {
        	this.index = index;
        }
		public int getIndex() {
			return index;
		}
    }
    
    public enum mObjectStaticIndex {
        BANK1 (0),
        NEST1 (1),
        POLICESTATION1(2),
        POLICESTATION2(3);
    
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
    
    protected EventState eventState;
    
    protected int mFrame;
    
    private float mPrevX;
    private float mPrevY;
    private float mStartX;
    private float mStartY;
    
    private float mZoom;
    private float mNewDistance;
    private float mPrevDistance;
    
    private int mScrollOffsetX;
    private int mScrollOffsetY;
    private int prevScrollOffsetX;
    private int prevScrollOffsetY;
    private int orientation;
    
	private Context mContext;
	private Bitmap mBackgroundImage;

	private long mLastTap = 0;
	private boolean zoomOut = false;
	private boolean moveAction = false;
	private float distance = 0.0f;

	private int canvasH=0;

	public PlayState(Context context) {
		mGrid = new Grid(context);

		setContext(context);
		
		mScrollOffsetX = 0;
		mScrollOffsetY = 0;
		
		mPrevDistance = 0.0f;		
		mZoom = 1.0f;
		
		SharedPreferences mPrefs = context.getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		
		//GameObjects
		mObjectArray = new ArrayList<GameObject>();
		
		mObjectArray.add(new CopObject("COP1",mGrid.mGridArray[9][9]));
		mObjectArray.add(new CopObject("COP2",mGrid.mGridArray[3][3]));
		mObjectArray.add(new ThiefObject("THIEF1",mGrid.mGridArray[4][7]));
		mObjectArray.add(new ThiefObject("THIEF2",mGrid.mGridArray[7][7]));

		//GameStaticObjects
		mObjectStaticArray = new ArrayList<GameStaticObject>();

		mObjectStaticArray.add(new BankObject("BANK1",mGrid.mGridArray[5][4]));
		mObjectStaticArray.add(new NestObject("NEST1",mGrid.mGridArray[2][1]));
		mObjectStaticArray.add(new PoliceStationObject("POLICESTATION1",mGrid.mGridArray[10][9]));
		mObjectStaticArray.add(new PoliceStationObject("POLICESTATION2",mGrid.mGridArray[10][10]));
		
		
		// Create the states
		copTurnState = new CopTurnState(this, mObjectArray, mObjectStaticArray, mGrid);
		copMoveState = new CopMoveState(this, mObjectArray, mObjectStaticArray, mGrid);
		copRollDiceState = new CopRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid);

		thiefRollDiceState = new ThiefRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid);
		thiefTurnState = new ThiefTurnState(this, mObjectArray, mObjectStaticArray, mGrid);
		thiefMoveState = new ThiefMoveState(this, mObjectArray, mObjectStaticArray, mGrid);
		
		setEventState(new EventState(this, mObjectArray, mObjectStaticArray, mGrid));
		
		// Set current state
		this.mCurrentState = getCopRollDiceState();

		mBackgroundImage = Bitmaps.instance(context).getBackgroundImage();
		
        mFrame = 0;
	}
	
	public void handleState(Canvas canvas) {
		if(mFrame++ == MAX_FPS) {
			mFrame = 1;
		}

		mCurrentState.handleState(mFrame);

		//Denna måste sparas då mCurrentState blir ett nytt objekt nedanför
		String currentObjectSelected = mCurrentState.getCurrentObjectSelected();

		mCurrentState = mCurrentState.getNextState();
		mCurrentState.setCurrentObjectSelected(currentObjectSelected);
		this.draw(canvas);

		canvasH=canvas.getHeight();
	}

	public void nextState(GameThread gt) {
	}

	public void draw(Canvas c) {
		c.scale(mZoom, mZoom);
		c.drawBitmap(mBackgroundImage, getOffsetX(), getOffsetY(), null);

		mCurrentState.doDraw(c, mZoom);

		mObjectArray.get(mObjectIndex.COP1.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.COP2.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.THIEF1.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.THIEF2.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		
		//this.drawHud(c,mZoom);
	}
	
	
	//movement of a game object
	public void doTouch(View v, MotionEvent event)
	{

		float x = event.getX();
		float y = event.getY();
		
		int eventaction = event.getAction();
		switch(eventaction)	{
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
						mScrollOffsetX = (int) -(x/mZoom - mScrollOffsetX - v.getWidth()/2.0f);
						mScrollOffsetY = (int) -(y/mZoom - getOffsetY() - v.getHeight()/2.0f);
						
						mZoom = 1.0f;
						
						zoomOut = false;
					}
					else {
						prevScrollOffsetX = mScrollOffsetX;
						prevScrollOffsetY = mScrollOffsetY;
						mScrollOffsetX = (int) -(x - mScrollOffsetX - v.getWidth());
						mScrollOffsetY = 0;
						
						zoomOut = true;
	
						if(v.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
							mZoom = v.getWidth() * 1.0f / mBackgroundImage.getWidth();
						}
						else {
							mZoom = v.getHeight() * 1.0f / mBackgroundImage.getHeight();
						}
					}
	
					// Kolla offset kriterier
					if(mScrollOffsetX > 0){
						mScrollOffsetX = 0;
					}
					else if(mScrollOffsetX*mZoom < (int) -((mBackgroundImage.getWidth())*mZoom - v.getWidth())) {
						mScrollOffsetX = (int) -((mBackgroundImage.getWidth()*mZoom - v.getWidth())/mZoom);
					}
					
					if(mScrollOffsetY > (int) (96-96*mZoom)) {
						mScrollOffsetY = (int) (96-96*mZoom);
					}
					else if(mScrollOffsetY < (int) - ((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom  + (48*2)/mZoom) + (int) (96-96*mZoom)) {
						mScrollOffsetY = (int) -((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom + (48*2)/mZoom) + (int) (96-96*mZoom);
					}
					
					mLastTap = 0;
				}
				
				mPrevX = x;
				mPrevY = y;
			}
			break;
			case MotionEvent.ACTION_MOVE: {
				distance = (float) Math.sqrt((x-mStartX)*(x-mStartX) + (y-mStartY)*(y-mStartY)); 
				
				if(distance > 24.0f) {
					moveAction = true;
					
					if(event.getPointerCount() == 1) {	
						mScrollOffsetX -= mPrevX - x;
						mScrollOffsetY -= mPrevY - y;
						
						if(mScrollOffsetX > 0) {
							mScrollOffsetX = 0;
						}
						else if(mScrollOffsetX*mZoom < (int) -((mBackgroundImage.getWidth())*mZoom - v.getWidth())) {
							mScrollOffsetX = (int) -((mBackgroundImage.getWidth()*mZoom - v.getWidth())/mZoom);
						}
						
						if(mScrollOffsetY > (int) (96-96*mZoom)){
							mScrollOffsetY = (int) (96-96*mZoom);
						}
						else if(mScrollOffsetY < (int) - ((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom  + (48*2)/mZoom) + (int) (96-96*mZoom)) {
							mScrollOffsetY = (int) -((mBackgroundImage.getHeight()*mZoom - v.getHeight())/mZoom + (48*2)/mZoom) + (int) (96-96*mZoom);
						}
						
					}
				}
	
				mPrevX = x;
				mPrevY = y;
			}
			break;
			case MotionEvent.ACTION_UP: {
				if(moveAction == false) { 
					if(zoomOut != true) {
						mCurrentState.doTouch(v, event);
					}
				}
			}
			break;
		}
	}

	protected CopTurnState getCopTurnState() {
		return (CopTurnState) copTurnState;
	}
	
	protected ThiefTurnState getThiefTurnState() {
		return (ThiefTurnState) thiefTurnState;
	}
	
	public void setOffsetX(int offset) {
		mScrollOffsetX = offset;
	}
	
	public void setOffsetY(int offset) {
		mScrollOffsetY = offset;
	}
	
	public int getOffsetX()	{
		return mScrollOffsetX;
	}
	
	public int getOffsetY()	{
		return mScrollOffsetY + PlayState.HUD_TOP_HEIGHT;
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
	
	protected void setEventState(EventState eventState) {
		this.eventState = eventState;
	}

	protected EventState getEventState() {
		return eventState;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public Context getContext() {
		return mContext;
	}

	public void saveState() {
		for(int i = 0; i < mObjectArray.size(); i++) {
			mObjectArray.get(i).saveState(mContext);
		}
	}
}