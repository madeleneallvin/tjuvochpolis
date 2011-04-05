package android.tjuvochpolis;

import android.content.Context;

public class Grid {

	public static int GRID_SIZE = 48;
	private int height = 18;
	private int width = 21;
	GridNode gridArray[][] = new GridNode[height][width];

	public Grid(Context context) {
		

		int[][] blocksType;
		blocksType = new int[height][width];
		String[] items;

		// Create an array with block types
		for(int j = 1; j < width+1; j++) {
			
			int arrayID = context.getResources().getIdentifier("c" + j,"array","android.tjuvochpolis"); 
	        items = context.getResources().getStringArray(arrayID);
	        
	        for (int i = 0; i < height; i++) {
	        	blocksType[i][j-1] = Integer.parseInt(items[i]);
	        }  
		}
		
		// Puts GridNodes in array with correct type

		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				
				gridArray[row][column] = new GridNode();
				int blockedType = blocksType[row][column];
				gridArray[row][column].setType(blockedType);
				gridArray[row][column].setPixelX(column*48);
				gridArray[row][column].setPixelY(row*48);
				gridArray[row][column].setNodeByX(column*48);
				gridArray[row][column].setNodeByY(row*48);
			}
		}

		// sets the neighbors
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {

				if (row == 0){
					gridArray[row][column].setUpNode(null);
				} else {
					gridArray[row][column].setUpNode(gridArray[row - 1][column]);
				}
					
				if (row == height - 1){
					gridArray[row][column].setDownNode(null);
				} else {
					gridArray[row][column].setDownNode(gridArray[row + 1][column]);
				}
					
				if (column == 0){
					gridArray[row][column].setLeftNode(null);
				} else {
					gridArray[row][column].setLeftNode(gridArray[row][column - 1]);
				}
					
				if (column == width - 1){
					gridArray[row][column].setRightNode(null);
				} else {
					gridArray[row][column].setRightNode(gridArray[row][column + 1]);
				}
					

			}
		}
	}
	
	public GridNode getGridNode(int row, int column)
	{
		return gridArray[row][column];
	}
	
	
}
