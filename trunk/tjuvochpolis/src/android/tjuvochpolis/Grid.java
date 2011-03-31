package android.tjuvochpolis;

import android.content.Context;

public class Grid {

	private int height = 18;
	private int width = 21;
	GridNode gridArray[][] = new GridNode[width][height];

	public Grid(Context context) {
		// Puts GridNodes in array
		//int[][] blocksType = context.getResources().getIntArray(R.array.blocksType);
		
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				
				gridArray[column][row] = new GridNode();
				//int blockedType = blocksType[column][row];
				//gridArray[column][row].setType(blockedType);
				gridArray[column][row].setX(column);
				gridArray[column][row].setY(row);
			}
		}

		// sets the neighbors
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {

				if (row == 0)
					gridArray[column][row].setUpNode(null);
				else
					gridArray[column][row]
							.setUpNode(gridArray[column][row - 1]);
				if (row == height - 1)
					gridArray[column][row].setDownNode(null);
				else
					gridArray[column][row]
							.setDownNode(gridArray[column][row + 1]);
				if (column == 0)
					gridArray[column][row].setLeftNode(null);
				else
					gridArray[column][row]
							.setLeftNode(gridArray[column - 1][row]);
				if (column == width - 1)
					gridArray[column][row].setRightNode(null);
				else
					gridArray[column][row]
							.setRightNode(gridArray[column + 1][row]);

			}
		}
	}
}
