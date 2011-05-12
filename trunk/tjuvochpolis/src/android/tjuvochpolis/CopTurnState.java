package android.tjuvochpolis;

import java.util.ArrayList;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.tjuvochpolis.PlayState.mObjectIndex;
import android.view.MotionEvent;
import android.view.View;

public class CopTurnState extends PlayOrderState {
	
	int turnSelect;
	boolean hasMoved = false;
	private boolean drawSplashCop = false;
	boolean everythingHasMoved = false;
	GameObject currentObject, lastSelected = null;

	private SplashButton turnButton;

	public CopTurnState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid, int index){	
		super(ps, gameObjects, gameStaticObjects, grid, index);
		
		SharedPreferences mPrefs = (ps.getContext()).getSharedPreferences("gamePrefs", Context.MODE_PRIVATE);
		everythingHasMoved = mPrefs.getBoolean("copEverythingHasMoved", false);
		hasMoved = mPrefs.getBoolean("copHasMoved", false);
		
		this.mGameObjects.get(mObjectIndex.COP1.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.COP2.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue());
		this.mGameObjects.get(mObjectIndex.COP3.getIndex()).doNodeWalker(this.mGameObjects.get(mObjectIndex.COP3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP3.getIndex()).getParentNode(), this.mGameObjects.get(mObjectIndex.COP3.getIndex()).getCurrentDiceValue());
		
	}
	@Override
	public void handleState(int frame){

	}

	public void doDraw(Canvas c, float mZoom){

		if (lastSelected != null){
			if(drawSplashCop == true && lastSelected.isMoving == false){
				drawSplashScreen(c , mPlayState.getContext());
			}
		}
		
		if(currentObject != null && lastSelected.getCurrentDiceValue() != 0)
		{
			drawHighlightSquare(currentObject, c, mPlayState.getOffsetX(), mPlayState.getOffsetY());
		}
	}

	public void drawSplashScreen(Canvas c, Context context) {

		Bitmap bankSplash = Bitmaps.instance(context).getThiefturnsplash();
		int left = c.getWidth()/6;
		int top = c.getHeight()/2 - (c.getWidth()/6)*2;
		Rect copTurnRect = new Rect(left, top, left+4*left, top+left*4);
		c.drawBitmap(bankSplash, null, copTurnRect, null);
	}

	public void doTouch(View v, MotionEvent event){
		
		if(event.getY() > PlayState.HUD_TOP_HEIGHT && event.getY() < v.getHeight() - PlayState.HUD_BOTTOM_HEIGHT)
		{
			//om x och y är giltiga destinationer
			int row = GridNode.getRow(event, mPlayState.getOffsetY());
			int col = GridNode.getCol(event, mPlayState.getOffsetX());

			GridNode clickedNode =	mGrid.getGridNode(row, col);
			currentObject =	clickedNode.getGameObject();
			mPlayState.setActiveObject(currentObject);
			
			if(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP3.getIndex()).getCurrentDiceValue() == 0  ){
				everythingHasMoved = true;
			}
			
			if((currentObject == null || currentObject.getClass().equals(ThiefObject.class) && currentObject.hasMoney()) && lastSelected != null && lastSelected.getClass() == CopObject.class && lastSelected.getCurrentDiceValue() != 0 ){
				for(ArrayList<GridNode> paths : lastSelected.getPossiblePaths()){
					if(paths.get(paths.size() - 1).equals(mGrid.getGridNode(row, col))){ 	
						hasMoved = true;
						lastSelected.setMovePath(paths);
						lastSelected.isMoving = true;
						lastSelected.setCurrentDiceValue(0);
						
						
					
						
						
						//sätt att en polis har tagit en tjuv och sen att tjuven är tagen, för att använda i CopMoveState
						if(currentObject != null && currentObject.getClass().equals(ThiefObject.class)){
							((CopObject)lastSelected).setThiefCaught((ThiefObject)currentObject);
							((ThiefObject)currentObject).setCaught(true);
						}
						
						setCurrentObjectSelected(lastSelected.getName());
					}

				}
				
				if(this.mGameObjects.get(mObjectIndex.COP1.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP2.getIndex()).getCurrentDiceValue() == 0 && this.mGameObjects.get(mObjectIndex.COP3.getIndex()).getCurrentDiceValue() == 0 ){
					
					drawSplashCop = true;
					
				}

			}
			else{
				lastSelected = currentObject;
			}
		}

	}
	
	public PlayOrderState getNextState() {

		if(hasMoved && everythingHasMoved == false){
			hasMoved = false;

			return mPlayState.copMoveState;

		}
		else if(everythingHasMoved == true){
			drawSplashCop = false;
			everythingHasMoved=false;
			
			return mPlayState.getThiefRollDiceState();

		} 

		return this;
	}
}
