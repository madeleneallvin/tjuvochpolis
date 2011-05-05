package android.tjuvochpolis;

import android.content.Context;

public class Grid {

	public static int GRID_SIZE = 48;
	private static int mNumRows = 18;
	private static int mNumCols = 21;
	GridNode mGridArray[][] = new GridNode[mNumRows][mNumCols];

	public Grid(Context context) {
		int[][] blocksType;
		blocksType = new int[mNumRows][mNumCols];
		String[] items;

		// Create an array with block types
		for(int j = 1; j < mNumCols+1; j++) {
			
			int arrayID = context.getResources().getIdentifier("c" + j,"array","android.tjuvochpolis"); 
	        items = context.getResources().getStringArray(arrayID);
	        
	        for (int i = 0; i < mNumRows; i++) {
	        	blocksType[i][j-1] = Integer.parseInt(items[i]);
	        }  
		}
		
		// Need to build the grid before adding the neighbors
		for (int column = 0; column < mNumCols; column++) {
			for (int row = 0; row < mNumRows; row++) {
				// Set values and attributes on each node
				mGridArray[row][column] = new GridNode(row, column);
				int blockedType = blocksType[row][column];
				mGridArray[row][column].setType(blockedType);
				
				// Set all GameObject and GameStaticObjects to null
				mGridArray[row][column].setGameObject(null);
				mGridArray[row][column].setGameStaticObject(null);
			}
		}
		
		// Sets the neighbors
		for (int column = 0; column < mNumCols; column++) {
			for (int row = 0; row < mNumRows; row++) {				
				if (row == 0){
					mGridArray[row][column].setUpNode(null);
				} else {
					mGridArray[row][column].setUpNode(mGridArray[row - 1][column]);
				}
					
				if (row == mNumRows - 1){
					mGridArray[row][column].setDownNode(null);
				} else {
					mGridArray[row][column].setDownNode(mGridArray[row + 1][column]);
				}
					
				if (column == 0){
					mGridArray[row][column].setLeftNode(null);
				} else {
					mGridArray[row][column].setLeftNode(mGridArray[row][column - 1]);
				}
					
				if (column == mNumCols - 1){
					mGridArray[row][column].setRightNode(null);
				} else {
					mGridArray[row][column].setRightNode(mGridArray[row][column + 1]);
				}
			}
		}
	}
	
	// Get a specific node depending on row and col
	public GridNode getGridNode(int row, int column)
	{
		return mGridArray[row][column];
	}
	
	// Get and set functions
	public static int getNumRows() {
		return mNumRows;
	}

	public static int getNumCols() {
		return mNumCols;
	}
}