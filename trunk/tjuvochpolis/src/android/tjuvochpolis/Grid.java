package android.tjuvochpolis;

import java.util.ArrayList;

import android.content.Context;

public class Grid {

	public static int GRID_SIZE = 48;
	private int mHeight = 18;
	private int mWidth = 21;
	 private static int mCurrentIndex;
	GridNode mGridArray[][] = new GridNode[mHeight][mWidth];

	public Grid(Context context) {
		

		int[][] blocksType;
		blocksType = new int[mHeight][mWidth];
		String[] items;

		// Create an array with block types
		for(int j = 1; j < mWidth+1; j++) {
			
			int arrayID = context.getResources().getIdentifier("c" + j,"array","android.tjuvochpolis"); 
	        items = context.getResources().getStringArray(arrayID);
	        
	        for (int i = 0; i < mHeight; i++) {
	        	blocksType[i][j-1] = Integer.parseInt(items[i]);
	        }  
		}
		
		// Puts GridNodes in array with correct type

		for (int column = 0; column < mWidth; column++) {
			for (int row = 0; row < mHeight; row++) {
				
				mGridArray[row][column] = new GridNode();
				int blockedType = blocksType[row][column];
				mGridArray[row][column].setType(blockedType);
				mGridArray[row][column].setPixelX(column*48);
				mGridArray[row][column].setPixelY(row*48);
				mGridArray[row][column].setNodeByX(column*48);
				mGridArray[row][column].setNodeByY(row*48);
			}
		}

		// sets the neighbors
		for (int column = 0; column < mWidth; column++) {
			for (int row = 0; row < mHeight; row++) {
				mGridArray[row][column].setGameObject(null); // sätter gameobject till null för att det ska gå att "kolla" på platserna utan att få nullpointerexception
				
				if (row == 0){
					mGridArray[row][column].setUpNode(null);
				} else {
					mGridArray[row][column].setUpNode(mGridArray[row - 1][column]);
				}
					
				if (row == mHeight - 1){
					mGridArray[row][column].setDownNode(null);
				} else {
					mGridArray[row][column].setDownNode(mGridArray[row + 1][column]);
				}
					
				if (column == 0){
					mGridArray[row][column].setLeftNode(null);
				} else {
					mGridArray[row][column].setLeftNode(mGridArray[row][column - 1]);
				}
					
				if (column == mWidth - 1){
					mGridArray[row][column].setRightNode(null);
				} else {
					mGridArray[row][column].setRightNode(mGridArray[row][column + 1]);
				}
					

			}
		}
	}
	

	public GridNode getGridNode(int row, int column)
	{
		return mGridArray[row][column];
	}




	
	
	
}
