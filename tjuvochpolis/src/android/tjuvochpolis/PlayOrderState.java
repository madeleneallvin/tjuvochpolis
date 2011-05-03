package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public abstract class PlayOrderState {
	protected ArrayList<GameObject> mGameObjects;
	protected ArrayList<GameStaticObject> mGameStaticObjects;

	protected Grid mGrid;
	protected PlayState mPlayState;
	protected PlayOrderState mNextState;
	protected float mAnimationStep = 15;
	protected int mCurrentAnimationStep = (int) mAnimationStep + 1;

	protected Bitmap mBlueSquareImage;
	protected Bitmap mGreenSquareImage;
	protected Bitmap mRedSquareImage;

	private String currentObjectSelected;
	
	private int tempX, tempY, tempXnext, tempYnext;

	public PlayOrderState(PlayState ps, ArrayList<GameObject> gameObjects, ArrayList<GameStaticObject> gameStaticObjects, Grid grid) {

		this.mPlayState = ps;
		this.mGameObjects = gameObjects;
		this.mGameStaticObjects = gameStaticObjects;
		this.mGrid = grid;

		Resources res = (ps.getContext()).getResources();
		mBlueSquareImage = BitmapFactory.decodeResource(res, R.drawable.blue_square); 
		mGreenSquareImage = BitmapFactory.decodeResource(res, R.drawable.green_square);
		mRedSquareImage = BitmapFactory.decodeResource(res, R.drawable.red_square);
	}

	/*public boolean canGoTo(int row, int col){
		if(grid.gridArray[row][col].getType() == GridNode.STREET)
		{			
			return true;
		}
		else if (grid.gridArray[row][col].getType() == GridNode.POLICE_STATION){
			return true;
		}
		else if (grid.gridArray[row][col].getType() == GridNode.TELEGRAPH){
			return true;
		}

		return false;
	}
	 */

	public abstract void handleState(int frame);

	public abstract void doTouch(View v, MotionEvent event);

	public abstract PlayOrderState getNextState();

	public void doDraw(Canvas c, float mZoom) {
		return;
	}


	protected void interpolatedMove(GameObject go, int frame) { 
		mCurrentAnimationStep++;

		if(mCurrentAnimationStep <= mAnimationStep) { 
			tempX = go.getMovePath().get(go.getCurrentPathPosition()-1).getNodeX();
			tempY = go.getMovePath().get(go.getCurrentPathPosition()-1).getNodeY();
			tempXnext = go.getMovePath().get(go.getCurrentPathPosition()).getNodeX();
			tempYnext = go.getMovePath().get(go.getCurrentPathPosition()).getNodeY();

			go.setDrawXPos(interpolate(tempX, tempXnext,mCurrentAnimationStep) );
			go.setDrawYPos(interpolate(go.getParentNode().getNodeY(), go.moveToRowCoordinate, mCurrentAnimationStep));
			go.setDrawYPos(interpolate(tempY, tempYnext,mCurrentAnimationStep));
		}
		else {
			mCurrentAnimationStep = 0;
			ArrayList<GridNode> path = go.getMovePath();

			//DET SISTA ELSE-SATSEN GÖR
			if( path.size() - go.getCurrentPathPosition() < 2) {
				go.isMoving = false;
				go.setParentNode(path.get(path.size()-1));
				go.getParentNode().setGameObject(go);
				go.setCurrentPathPosition(0);
				
				//återställer animationstep så vi har rätt defaultvärde till nästa objekt
				mCurrentAnimationStep = (int) mAnimationStep + 1;
			}
			else {
				GridNode moveToNode = path.get(go.getCurrentPathPosition() + 1); //detta kommer inte att ske om pathsize är mindre än 2.

				go.moveToColCoordinate = moveToNode.getNodeX();
				go.moveToRowCoordinate = moveToNode.getNodeY();

				go.setParentNode(path.get(go.getCurrentPathPosition()));
			}
			
			if(go.isMoving) {
				go.setCurrentPathPosition(go.getCurrentPathPosition() + 1);
			}
			
			path.get(0).setGameObject(null);
		}
	}

	private float interpolate(int gridStart, int gridEnd, int frame) {
		return ((float)gridStart*Grid.GRID_SIZE) + ((float) gridEnd*Grid.GRID_SIZE - gridStart*Grid.GRID_SIZE) * ((float) mCurrentAnimationStep/mAnimationStep);
	}

	public void drawHighlightSquare(GameObject go, Canvas canvas, int OffsetX, int OffsetY) {
		Paint paint = new Paint();

		ArrayList<GridNode> endSquares = new ArrayList<GridNode>();
		ArrayList<GridNode> normalSquares = new ArrayList<GridNode>();

		for(ArrayList<GridNode> paths : go.getPossiblePaths()) {
			endSquares.add(paths.get(paths.size() - 1));
		}

		for(ArrayList<GridNode> paths : go.getPossiblePaths()) {
			for(GridNode node : paths) {
				if(!normalSquares.contains(node) && !endSquares.contains(node)) {
					normalSquares.add(node);
				}
			}
		}

		paint.setARGB(128, 0, 255, 0);
		
		for(GridNode node : endSquares) {
			int xPos = node.getPixelX() + OffsetX;
			int yPos = node.getPixelY() + OffsetY;
			Rect rec = new Rect(xPos, yPos, xPos + Grid.GRID_SIZE, yPos + Grid.GRID_SIZE);
			if(node.getGameStaticObject() == null) {
				canvas.drawBitmap(mGreenSquareImage, null, rec, null);
			}
			else {
				canvas.drawBitmap(mRedSquareImage, null, rec, null);
			}
		}

		paint.setARGB(110, 0, 10, 200);
		
		for(GridNode node : normalSquares) {
			int xPos = node.getPixelX() + OffsetX;
			int yPos = node.getPixelY() + OffsetY;
			Rect rec = new Rect(xPos, yPos, xPos + Grid.GRID_SIZE, yPos + Grid.GRID_SIZE);
			canvas.drawBitmap(mBlueSquareImage, null, rec, null);	
		}
	}

	public void setCurrentObjectSelected(String currentObjectSelected) {
		this.currentObjectSelected = currentObjectSelected;
	}

	public String getCurrentObjectSelected() {
		return currentObjectSelected;
	}
}