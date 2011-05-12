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
        COP3 (2),
        THIEF1(3),
        THIEF2 (4),
        THIEF3 (5);
               
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
        BANK2 (1),
        BANK3 (2),
        BANK4 (3),
        BANK5 (4),
        BANK6 (5),
        BANK7 (6),
        BANK8 (7),
        NEST1 (8),
        NEST2 (9),
        NEST3 (10),
        NEST4 (11),
        POLICESTATION1(12),
        POLICESTATION2(13),
        JAIL1(14),
        JAIL2(15),
        JAIL3(16);
    
        private final int index;
        mObjectStaticIndex(int index) {
        	this.index = index;
        }
		public int getIndex() {
			return index;
		}
    }
    
    protected PlayOrderState mCurrentState;
    protected PlayOrderState mPreviousState;
    
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

		mObjectArray.add(new CopObject("COP1",mGrid.mGridArray[mPrefs.getInt("COP1_row",9)][mPrefs.getInt("COP1_col",12)], mPrefs.getInt("COP1_diceValue",0), mPrefs.getInt("COP1_money",0)));
		mObjectArray.add(new CopObject("COP2",mGrid.mGridArray[mPrefs.getInt("COP2_row",9)][mPrefs.getInt("COP2_col",11)], mPrefs.getInt("COP2_diceValue",0), mPrefs.getInt("COP2_money",0)));
		mObjectArray.add(new CopObject("COP3",mGrid.mGridArray[mPrefs.getInt("COP3_row",8)][mPrefs.getInt("COP3_col",11)], mPrefs.getInt("COP3_diceValue",0), mPrefs.getInt("COP3_money",0)));
		
		mObjectArray.add(new ThiefObject("THIEF1",mGrid.mGridArray[mPrefs.getInt("THIEF1_row",12)][mPrefs.getInt("THIEF1_col",19)], mPrefs.getInt("THIEF1_diceValue",0), mPrefs.getInt("THIEF1_money",0)));
		mObjectArray.add(new ThiefObject("THIEF2",mGrid.mGridArray[mPrefs.getInt("THIEF2_row",12)][mPrefs.getInt("THIEF2_col",1)], mPrefs.getInt("THIEF2_diceValue",0), mPrefs.getInt("THIEF2_money",0)));
		mObjectArray.add(new ThiefObject("THIEF3",mGrid.mGridArray[mPrefs.getInt("THIEF3_row",1)][mPrefs.getInt("THIEF3_col",2)], mPrefs.getInt("THIEF3_diceValue",0), mPrefs.getInt("THIEF3_money",0)));

		//GameStaticObjects
		mObjectStaticArray = new ArrayList<GameStaticObject>();

		//Banks
		mObjectStaticArray.add(new BankObject("BANK1",mGrid.mGridArray[5][4]));
		mObjectStaticArray.add(new BankObject("BANK2",mGrid.mGridArray[5][16]));
		mObjectStaticArray.add(new BankObject("BANK3",mGrid.mGridArray[6][10]));
		mObjectStaticArray.add(new BankObject("BANK4",mGrid.mGridArray[12][5]));
		mObjectStaticArray.add(new BankObject("BANK5",mGrid.mGridArray[12][15]));
		mObjectStaticArray.add(new BankObject("BANK6",mGrid.mGridArray[16][2]));
		mObjectStaticArray.add(new BankObject("BANK7",mGrid.mGridArray[16][10]));
		mObjectStaticArray.add(new BankObject("BANK8",mGrid.mGridArray[16][18]));
		
		//Nests
		mObjectStaticArray.add(new NestObject("NEST1",mGrid.mGridArray[1][2]));
		mObjectStaticArray.add(new NestObject("NEST2",mGrid.mGridArray[1][16]));
		mObjectStaticArray.add(new NestObject("NEST3",mGrid.mGridArray[12][1]));
		mObjectStaticArray.add(new NestObject("NEST4",mGrid.mGridArray[12][19]));
		
		//Police stations
		mObjectStaticArray.add(new PoliceStationObject("POLICESTATION1",mGrid.mGridArray[8][10]));
		mObjectStaticArray.add(new PoliceStationObject("POLICESTATION2",mGrid.mGridArray[9][10]));
		
		//Jails
		mObjectStaticArray.add(new JailObject("JAIL1",mGrid.mGridArray[8][11]));
		mObjectStaticArray.add(new JailObject("JAIL2",mGrid.mGridArray[9][11]));
		mObjectStaticArray.add(new JailObject("JAIL3",mGrid.mGridArray[9][12]));
		
		
		// Create the states
		copTurnState = new CopTurnState(this, mObjectArray, mObjectStaticArray, mGrid, 0);
		copMoveState = new CopMoveState(this, mObjectArray, mObjectStaticArray, mGrid, 1);
		copRollDiceState = new CopRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid, 2);

		thiefRollDiceState = new ThiefRollDiceState(this, mObjectArray, mObjectStaticArray, mGrid, 3);
		thiefTurnState = new ThiefTurnState(this, mObjectArray, mObjectStaticArray, mGrid, 4);
		thiefMoveState = new ThiefMoveState(this, mObjectArray, mObjectStaticArray, mGrid, 5);
		
		setEventState(new EventState(this, mObjectArray, mObjectStaticArray, mGrid, 6));
		
		// Set current state
		int currentState = mPrefs.getInt("currentState", 2);
		
		Log.i("Current state", "" + currentState);
		
		switch(currentState)
		{
			case 0:
				this.mCurrentState = getCopTurnState();
				break;
			case 1:
				this.mCurrentState = getCopMoveState();
				break;
			case 2:
				this.mCurrentState = getCopRollDiceState();
				break;
			case 3:
				this.mCurrentState = getThiefRollDiceState();
				break;
			case 4:
				this.mCurrentState = getThiefTurnState();
				break;
			case 5:
				this.mCurrentState = getThiefMoveState();
				break;
			default:
				this.mCurrentState = getCopRollDiceState();
				break;
		}
		//this.mCurrentState = getCopRollDiceState();

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
	}

	public void nextState(GameThread gt) {
	}

	public void draw(Canvas c) {
		c.scale(mZoom, mZoom);
		c.drawBitmap(mBackgroundImage, getOffsetX(), getOffsetY(), null);

		mObjectArray.get(mObjectIndex.COP1.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.COP2.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.COP3.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.THIEF1.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.THIEF2.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);
		mObjectArray.get(mObjectIndex.THIEF3.getIndex()).doDraw(c, getOffsetX(), getOffsetY(), mContext);

		mCurrentState.doDraw(c, mZoom);		
		
		// Draw the splash screen if the current state is EventState
		if(this.mCurrentState.getClass() == EventState.class){
			((EventState)mCurrentState).drawSplash(c, mZoom);
		}
		//rita splash för CopTurnState och thiefTurnState
		
	//	if(this.mCurrentState.getClass() == CopTurnState.class){
	//		((CopTurnState)mCurrentState).drawSplash = true;
	//	}
		
	/*	if(this.mCurrentState.getClass() == ThiefTurnState.class){
			((ThiefTurnState)mCurrentState).drawSplash(c, mZoom);
		}*/
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

	public PlayOrderState getCurrentPlayOrderState()
	{
		return mCurrentState;
	}
	
	protected CopTurnState getCopTurnState() {
		return (CopTurnState) copTurnState;
	}
	
	protected ThiefTurnState getThiefTurnState() {
		return (ThiefTurnState) thiefTurnState;
	}
	protected CopMoveState getCopMoveState()
	{
		return (CopMoveState) copMoveState;
	}
	protected ThiefMoveState getThiefMoveState()
	{
		
		return (ThiefMoveState) thiefMoveState;
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
	
	public void saveState()
	{
		Log.i("PlayState", "savestate");
		
		SharedPreferences mPrefs = mContext.getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor ed = mPrefs.edit();
		
		int currentState = mCurrentState.getIndex();
		switch(currentState)
		{
			case 0:
				ed.putInt("currentState", 0);
				ed.putBoolean("copEverythingHasMoved", getCopTurnState().everythingHasMoved);
				ed.putBoolean("copHasMoved", getCopTurnState().hasMoved);
				break;
			case 1:
				ed.putInt("currentState", 1);
				break;
			case 2:
				ed.putInt("currentState", 2);
				break;
			case 3:
				ed.putInt("currentState", 3);
				break;
			case 4:
				ed.putInt("currentState", 4);
				ed.putBoolean("thiefEverythingHasMoved", getThiefTurnState().everythingHasMoved);
				ed.putBoolean("thiefHasMoved", getThiefTurnState().hasMoved);
				break;
			case 5:
				ed.putInt("currentState", 5);
				break;
			default:
				ed.putInt("currentState", 2);
				break;
		}
		
		ed.commit();
		
		for(int i = 0; i < mObjectArray.size(); i++)
		{
			mObjectArray.get(i).saveState(mContext);
		}
	}
	
	public GameObject getGameObject(mObjectIndex objectIndex)
	{
		return mObjectArray.get(objectIndex.index);
	}
}